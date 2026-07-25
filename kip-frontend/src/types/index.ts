export type TransactionStatus = "PENDING" | "SETTLED" | "CANCELLED";

export interface Transaction {
  id: string;
  linkedAccount: LinkedAccount;
  amount: number;
  description: string | null;
  transactionDate: string | null;
  type: string | null;
  status: TransactionStatus;
  externalId: string;
  merchantName: string | null;
  syncedAt: string | null;
}
export interface LinkedAccount {
  id: string;
  externalId: string;
  accountNickname: string | null;
  linkedAt: string | null;
  balance: number;
}
export interface Snapshot {
  balance: number;
  pendingTotal: number;
  safeToSpend: number;
}
export interface WeeklyReport {
  startDate: string | null;
  endDate: string | null;
  totalSpent: number;
  transactionCount: number;
  transaction: Transaction[];
  categoryBreakdown: Record<string, number>;
  kipperFeedback: string | null;
  previousWeekTotal: number;
  spendingChange: number;
  anomalies: Transaction[];
}
