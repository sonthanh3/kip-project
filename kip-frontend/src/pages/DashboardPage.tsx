import { useState, useEffect } from "react";
import {
  getLinkedAccounts,
  getSnapshot,
  getTransactions,
  chargeTransaction,
} from "../services/api";
import type { LinkedAccount, Snapshot, Transaction } from "../types";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";

export default function DashboardPage() {
  const [linkedAccount, setLinkedAccount] = useState<LinkedAccount | null>(
    null,
  );
  const [snapshot, setSnapshot] = useState<Snapshot | null>(null);
  const [transactions, setTransactions] = useState<Transaction[]>([]);
  const [loading, setLoading] = useState(true);
  const [merchantName, setMerchantName] = useState("");
  const [amount, setAmount] = useState("");

  useEffect(() => {
    const loadData = async () => {
      const accounts = await getLinkedAccounts();

      if (accounts.length === 0) {
        setLoading(false);
        return;
      }

      const account = accounts[accounts.length - 1];
      setLinkedAccount(account);

      const [snapshotData, transactionsData] = await Promise.all([
        getSnapshot(account.id),
        getTransactions(account.id),
      ]);

      setSnapshot(snapshotData);
      setTransactions(transactionsData);
      setLoading(false);
    };

    loadData();
  }, []);

  const handleSimulate = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!linkedAccount) return;

    await chargeTransaction(
      linkedAccount.externalId,
      parseFloat(amount),
      merchantName,
    );

    // đợi 1 chút cho webhook xử lý xong
    await new Promise((resolve) => setTimeout(resolve, 500));

    // reload lại data
    const [snapshotData, transactionsData] = await Promise.all([
      getSnapshot(linkedAccount.id),
      getTransactions(linkedAccount.id),
    ]);
    setSnapshot(snapshotData);
    setTransactions(transactionsData);

    setMerchantName("");
    setAmount("");
  };

  if (loading) return <div>...Loading...</div>;
  if (!linkedAccount) return <div>No linked accounts yet.</div>;

  return (
    <div className="p-8 max-w-2xl mx-auto flex flex-col gap-6">
      <Card>
        <CardHeader>
          <CardTitle className="text-neutral-800">Safe to spend</CardTitle>
        </CardHeader>
        <CardContent>
          <p className="text-4xl font-medium text-neutral-800">
            ${snapshot?.safeToSpend.toFixed(2)}
          </p>
          <div className="flex gap-6 mt-4 text-sm text-neutral-500">
            <p>Balance: ${snapshot?.balance.toFixed(2)}</p>
            <p>Pending: ${snapshot?.pendingTotal.toFixed(2)}</p>
          </div>
        </CardContent>
      </Card>
      <Card>
        <CardHeader>
          <CardTitle className="text-neutral-800">
            Recent transactions
          </CardTitle>
        </CardHeader>
        <CardContent>
          {transactions.length === 0 ? (
            <p className="text-neutral-500">No transactions yet.</p>
          ) : (
            <div className="flex flex-col gap-3">
              {transactions.map((t) => (
                <div
                  key={t.id}
                  className="flex justify-between items-center border-b border-neutral-100 pb-2"
                >
                  <div>
                    <p className="text-neutral-800">
                      {t.merchantName || "Unknown merchant"}
                    </p>
                    <p className="text-sm text-neutral-500">{t.status}</p>
                  </div>
                  <p className="text-neutral-800 font-medium">
                    ${t.amount.toFixed(2)}
                  </p>
                </div>
              ))}
            </div>
          )}
        </CardContent>
      </Card>
      <Card>
        <CardHeader>
          <CardTitle className="text-neutral-800">
            Simulate transaction
          </CardTitle>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSimulate} className="flex flex-col gap-4">
            <div className="flex flex-col gap-2">
              <Label htmlFor="merchantName">Merchant name</Label>
              <Input
                id="merchantName"
                value={merchantName}
                onChange={(e) => setMerchantName(e.target.value)}
                required
              />
            </div>
            <div className="flex flex-col gap-2">
              <Label htmlFor="amount">Amount</Label>
              <Input
                id="amount"
                type="number"
                step="0.01"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
                required
              />
            </div>
            <Button type="submit">Charge</Button>
          </form>
        </CardContent>
      </Card>
    </div>
  );
}
