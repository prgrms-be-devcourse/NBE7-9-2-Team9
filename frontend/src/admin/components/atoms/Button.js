import React from "react";
import "./Button.css";

const Button = ({
  children,
  onClick,
  variant = "primary",
  size = "medium",
  disabled = false,
  type = "button",
  className = "",
}) => {
  const buttonClasses = `btn btn-${variant} btn-${size} ${className}`.trim();

  return (
    <button
      type={type}
      className={buttonClasses}
      onClick={onClick}
      disabled={disabled}
    >
      {children}
    </button>
  );
};

export default Button;
