import { useNavigate } from "react-router-dom";

export default function PlanCard({ plan }) {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/plan/detail/${plan.id}`);
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toISOString().split("T")[0];
  };

  return (
    <div
      className="border rounded-2xl shadow-md p-4 mb-4 hover:shadow-lg transition cursor-pointer"
      onClick={handleClick}
    >
      <h2 className="text-xl font-semibold text-blue-600 hover:underline">
        {plan.title}
      </h2>
      <p className="text-gray-600 mt-2">
        {plan.content.length > 30
          ? plan.content.slice(0, 30) + "..."
          : plan.content}
      </p>
      <p className="text-sm text-gray-500 mt-2">
        {formatDate(plan.startDate)} ~ {formatDate(plan.endDate)}
      </p>
    </div>
  );
}
