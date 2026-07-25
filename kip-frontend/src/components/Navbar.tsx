import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { Button } from "@/components/ui/button";

export default function Navbar() {
  const { logout, token } = useAuth();
  const navigate = useNavigate();

  if (!token) return null;

  const handleLogout = () => {
    logout();
    alert("Logged Out Successfully");
    navigate("/login");
  };

  return (
    <nav className="border-b border-neutral-200 bg-white px-8 py-4 flex items-center justify-between">
      <div className="flex items-center gap-8">
        <Link to="/dashboard" className="text-lg font-medium text-neutral-800">
          Kip
        </Link>
        <Link
          to="/dashboard"
          className="text-sm text-neutral-600 hover:text-neutral-800"
        >
          Dashboard
        </Link>
        <Link
          to="/weekly-report"
          className="text-sm text-neutral-600 hover:text-neutral-800"
        >
          Weekly Report
        </Link>
      </div>
      <Button variant="outline" onClick={handleLogout}>
        Logout
      </Button>
    </nav>
  );
}
