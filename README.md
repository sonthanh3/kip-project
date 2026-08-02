# Kip — Keep Track

A personal finance tool designed for someone rebuilding their financial life after financial hardship. Calm, factual, no shame-based UX, no debt products — just clarity on what actually happened to your money and what's safe to spend today.

## Demo
[Watch the demo video](https://youtu.be/P8Vru4Q7018)

## Why this exists

Bankruptcy usually traces back to poor money visibility, not just poor decisions. Kip's job is to make that visibility effortless: one honest number for what's safe to spend, a weekly summary of where money went, and a neutral AI companion (Kipper) that reflects spending patterns back without judgment.

## Architecture

Kip is split into two services on purpose, mirroring how a real bank integration (like Plaid) works:

```
MockBank (simulated bank)  --webhook-->  Kip (the actual product)
     |                                        |
  PostgreSQL                              PostgreSQL
```

**MockBank** simulates a real bank. It owns `BankAccount` and `BankTransaction`, exposes a `/charge` endpoint that simulates a card swipe, and pushes new transactions to Kip via webhook.

**Kip** is the product. It receives webhooks from MockBank, deduplicates them via an `external_id` unique constraint (idempotency), auto-links accounts on first sight, and computes spending insights on top of the synced data.

I didn't have access to a real bank API, so I built MockBank as a stand-in and designed the same webhook + idempotency architecture a production Plaid integration would need — including handling duplicate webhook deliveries safely.

## Tech stack

| Layer | Choice |
|---|---|
| Backend | Java 21, Spring Boot 3.5, Spring Data JPA, Spring Security |
| Database | PostgreSQL (separate database per service) |
| Auth | JWT (jjwt) |
| AI | Claude API (Anthropic) — powers Kipper's weekly feedback, with a rule-based fallback if the API is unavailable |
| Frontend | React, TypeScript, Vite, Tailwind CSS, shadcn/ui |
| Containerization | Docker, Docker Compose |

## Core features

- **Webhook sync with idempotency** — MockBank pushes transactions to Kip; duplicate webhook deliveries are detected via `external_id` and safely ignored instead of creating duplicate records.
- **Auto-linking accounts** — the first webhook for a new MockBank account automatically creates a `LinkedAccount` in Kip, simulating an OAuth-style bank link without requiring a separate linking flow.
- **SmartMoneySnapshot** — a real-time, computed (not stored) number: balance minus pending transactions. One honest figure, not a wall of data.
- **Weekly Report** — spending totals, category breakdown, week-over-week comparison, and anomaly detection (transactions well above the account's average).
- **Kipper** — an AI companion that reads the weekly report and gives brief, neutral, shame-free feedback. Falls back to simple rule-based feedback if the Claude API is unreachable, so the feature degrades gracefully instead of breaking.
- **JWT authentication** — registration and login issue a signed token; protected endpoints verify it via a custom filter in the Spring Security chain.
- **Simulate Transaction** — a demo control on the dashboard that calls MockBank's `/charge` endpoint directly from the UI, so anyone reviewing the project can trigger the full webhook → sync → snapshot update flow without touching Postman.

## Design principles

- No "add a credit card" feature, no BNPL tracking, nothing that normalizes new debt.
- Reconciliation is framed as reassurance ("this matches what you expected"), not audit.
- One prominent, conservative "safe to spend" number instead of a dashboard full of figures to interpret.
- Neutral tone everywhere. No red "overspent" banners, no streaks, no guilt copy — Kipper's feedback is written to inform, not shame.

## Running it locally

Requires Docker Desktop.

```bash
git clone https://github.com/sonthanh3/kip-project.git
cd kip-project
docker-compose up --build
```

This starts both databases and both backend services in one command:

- MockBank → `localhost:8080`
- Kip → `localhost:8081`

For the frontend:

```bash
cd kip-frontend
npm install
npm run dev
```

Frontend runs at `localhost:5173`.

You'll need a Claude API key for Kipper's AI feedback — set it as the `CLAUDE_API_KEY` environment variable before starting Kip. Without it, Kipper automatically falls back to rule-based feedback.

### Trying it out

1. Register an account on the frontend and log in.
2. On the dashboard, use "Simulate Transaction" to charge a mock card swipe.
3. Watch the SmartMoneySnapshot and transaction list update automatically — this round-trips through MockBank's webhook to Kip in real time.
4. Visit the Weekly Report page to see the category breakdown, week-over-week trend, and Kipper's take on the week.

## What I'd build next

- Real categorization (currently all synced transactions land in "Uncategorized" — the category field exists in the schema but nothing assigns it yet).
- A budget/spending-limit line on top of SmartMoneySnapshot, so the "safe to spend" number can reflect a self-imposed cap, not just balance minus pending.
- OAuth login as an alternative to email/password.
- Proper HTTP status handling across all service exceptions (currently only auth errors return specific codes; other failures fall back to a generic 500).

## A note on scope

This project is intentionally split into two services to practice the same problems a real bank integration creates: idempotency, eventual consistency, and webhook reliability. Where a shortcut wouldn't teach me anything (email-based OAuth, categorization ML), I deferred it. Where the shortcut would have hidden a real engineering problem (using a single shared database instead of a webhook boundary, storing plaintext passwords), I didn't take it.
