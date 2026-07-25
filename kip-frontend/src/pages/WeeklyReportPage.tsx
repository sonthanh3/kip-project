import { useState, useEffect } from "react";
import { getLinkedAccounts, getWeeklyReport } from "../services/api";
import type { LinkedAccount, WeeklyReport } from "../types";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

export default function WeeklyReportPage() {
  const [linkedAccount, setLinkedAccount] = useState<LinkedAccount | null>(
    null,
  );
  const [report, setReport] = useState<WeeklyReport | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadData = async () => {
      const accounts = await getLinkedAccounts();

      if (accounts.length === 0) {
        setLoading(false);
        return;
      }

      const account = accounts[accounts.length - 1];
      setLinkedAccount(account);

      const reportData = await getWeeklyReport(account.id);
      setReport(reportData);
      setLoading(false);
    };

    loadData();
  }, []);

  if (loading) return <div>...Loading...</div>;
  if (!linkedAccount) return <div>No linked accounts yet.</div>;

  return (
    <div className="p-8 max-w-2xl mx-auto flex flex-col gap-6">
      <Card>
        <CardHeader>
          <CardTitle className="text-neutral-800">Kipper's take</CardTitle>
        </CardHeader>
        <CardContent>
          <p className="text-neutral-600">{report?.kipperFeedback}</p>
        </CardContent>
      </Card>
      <Card>
        <CardHeader>
          <CardTitle className="text-neutral-800">This week</CardTitle>
        </CardHeader>
        <CardContent>
          <p className="text-4xl font-medium text-neutral-800">
            ${report?.totalSpent.toFixed(2)}
          </p>
          <p className="text-sm text-neutral-500 mt-2">
            {report && report.spendingChange !== 0
              ? `${report.spendingChange > 0 ? "+" : ""}${report.spendingChange.toFixed(1)}% vs last week ($${report.previousWeekTotal.toFixed(2)})`
              : "No comparison data yet"}
          </p>
        </CardContent>
      </Card>
      <Card>
        <CardHeader>
          <CardTitle className="text-neutral-800">Category breakdown</CardTitle>
        </CardHeader>
        <CardContent>
          {report && Object.keys(report.categoryBreakdown).length === 0 ? (
            <p className="text-neutral-500">
              No categorized spending this week.
            </p>
          ) : (
            <div className="flex flex-col gap-2">
              {report &&
                Object.entries(report.categoryBreakdown).map(
                  ([category, amount]) => (
                    <div
                      key={category}
                      className="flex justify-between text-neutral-700"
                    >
                      <p>{category}</p>
                      <p className="font-medium">${amount.toFixed(2)}</p>
                    </div>
                  ),
                )}
            </div>
          )}
        </CardContent>
      </Card>
      {report && report.anomalies.length > 0 && (
        <Card>
          <CardHeader>
            <CardTitle className="text-neutral-800">
              Worth a second look
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="flex flex-col gap-2">
              {report.anomalies.map((t) => (
                <div
                  key={t.id}
                  className="flex justify-between text-neutral-700"
                >
                  <p>{t.merchantName || "Unknown merchant"}</p>
                  <p className="font-medium">${t.amount.toFixed(2)}</p>
                </div>
              ))}
            </div>
          </CardContent>
        </Card>
      )}
      <Card>
        <CardHeader>
          <CardTitle className="text-neutral-800">All transactions</CardTitle>
        </CardHeader>
        <CardContent>
          {report && report.transaction.length === 0 ? (
            <p className="text-neutral-500">No transactions this week.</p>
          ) : (
            <div className="flex flex-col gap-3">
              {report?.transaction.map((t) => (
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
    </div>
  );
}
