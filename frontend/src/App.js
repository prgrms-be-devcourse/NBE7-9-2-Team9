import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import logo from "./logo.svg";
import "./App.css";
import AdminApp from "./admin/AdminApp";

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/admin/*" element={<AdminApp />} />
          <Route
            path="/"
            element={
              <div>
                <header className="App-header">
                  <img src={logo} className="App-logo" alt="logo" />
                  <p>
                    Edit <code>src/App.js</code> and save to reload.
                  </p>
                  <a className="App-link" href="/admin">
                    관리자 페이지로 이동
                  </a>
                </header>
              </div>
            }
          />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
