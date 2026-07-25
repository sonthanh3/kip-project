import axios from "axios";
import type {
  LinkedAccount,
  Snapshot,
  Transaction,
  WeeklyReport,
} from "../types";

const kipApi = axios.create({
  baseURL: "http://localhost:8081",
});

const mockbankApi = axios.create({
  baseURL: "http://localhost:8080",
});

kipApi.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export { kipApi, mockbankApi };

export const getLinkedAccounts = async (): Promise<LinkedAccount[]> => {
  const response = await kipApi.get("/linked-accounts");
  return response.data;
};

export const getSnapshot = async (
  linkedAccountId: string,
): Promise<Snapshot> => {
  const response = await kipApi.get(
    `/linked-accounts/${linkedAccountId}/snapshot`,
  );
  return response.data;
};

export const getTransactions = async (
  linkedAccountId: string,
): Promise<Transaction[]> => {
  const response = await kipApi.get(
    `/linked-accounts/${linkedAccountId}/transactions`,
  );
  return response.data;
};

export const chargeTransaction = async (
  accountId: string,
  amount: number,
  merchantName: string,
) => {
  const response = await mockbankApi.post("/transactions/charge", {
    accountId,
    amount,
    merchantName,
  });
  return response.data;
};

export const getWeeklyReport = async (
  linkedAccountId: string,
): Promise<WeeklyReport> => {
  const response = await kipApi.get(
    `/linked-accounts/${linkedAccountId}/weekly-report`,
  );
  return response.data;
};
