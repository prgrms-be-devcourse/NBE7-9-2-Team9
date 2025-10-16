import React from "react";
import "./Textarea.css";

const Textarea = ({
  placeholder = "",
  value = "",
  onChange,
  label = "",
  required = false,
  error = "",
  className = "",
  rows = 4,
  ...props
}) => {
  const textareaClasses = `textarea ${
    error ? "textarea-error" : ""
  } ${className}`.trim();

  return (
    <div className="textarea-group">
      {label && (
        <label className="textarea-label">
          {label}
          {required && <span className="required">*</span>}
        </label>
      )}
      <textarea
        placeholder={placeholder}
        value={value}
        onChange={onChange}
        className={textareaClasses}
        required={required}
        rows={rows}
        {...props}
      />
      {error && <span className="textarea-error-message">{error}</span>}
    </div>
  );
};

export default Textarea;
