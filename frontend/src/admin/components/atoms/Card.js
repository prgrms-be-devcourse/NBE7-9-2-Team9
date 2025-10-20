import React from "react";
import "./Card.css";

const Card = ({ children, className = "", onClick, hoverable = false }) => {
  const cardClasses = `card ${
    hoverable ? "card-hoverable" : ""
  } ${className}`.trim();

  return (
    <div className={cardClasses} onClick={onClick}>
      {children}
    </div>
  );
};

export default Card;
