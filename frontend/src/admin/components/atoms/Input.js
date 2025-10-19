import React from "react";
import "./Input.css";

const Input = ({
  type = "text",
  placeholder = "",
  value = "",
  onChange,
  label = "",
  required = false,
  error = "",
  className = "",
  ...props
}) => {
  const inputClasses = `input ${
    error ? "input-error" : ""
  } ${className}`.trim();

  return (
    <div className="input-group">
      {label && (
        <label className="input-label">
          {label}
          {required && <span className="required">*</span>}
        </label>
      )}
      <input
        type={type}
        placeholder={placeholder}
        value={value}
        onChange={onChange}
        className={inputClasses}
        required={required}
        {...props}
      />
      {error && <span className="input-error-message">{error}</span>}
    </div>
  );
};

export default Input;
