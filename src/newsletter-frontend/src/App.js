// App.js
import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import NewsletterScheduledForm from './NewsletterScheduledForm';
import NewsletterForm from './NewsletterForm';
import MetricsDashboard from './MetricsDashboard';

function App() {
  // State to manage sidebar visibility and selected option
  const [isOpen, setIsOpen] = useState(false);
  const [selectedOption, setSelectedOption] = useState('');

  // Toggle the sidebar
  const toggleSidebar = () => {
    setIsOpen(!isOpen);
  };

  // Handle selecting an option
  const handleOptionClick = (option) => {
    setSelectedOption(option);
    toggleSidebar(); // Close sidebar after selecting
  };

  return (
    <div className="App d-flex">
      {/* Header with Bootstrap Navbar */}
      <nav className="navbar navbar-dark bg-dark w-100">
        <button className="btn btn-dark" onClick={toggleSidebar}>
          ☰
        </button>
        <h1 className="text-light mx-3">My App</h1>
      </nav>

      {/* Sidebar (Bootstrap Offcanvas) */}
      <div className={`offcanvas offcanvas-start ${isOpen ? 'show' : ''}`} tabIndex="-1" style={{ visibility: isOpen ? 'visible' : 'hidden' }}>
        <div className="offcanvas-header">
          <h5 className="offcanvas-title">Menu</h5>
          <button type="button" className="btn-close text-reset" onClick={toggleSidebar}></button>
        </div>
        <div className="offcanvas-body">
          <ul className="list-unstyled">
            <li><a href="#send-newsletter" className="nav-link" onClick={() => handleOptionClick('send-newsletter')}>Send Newsletter</a></li>
            <li><a href="#schedule-newsletter" className="nav-link" onClick={() => handleOptionClick('schedule-newsletter')}>Schedule Newsletter</a></li>
            <li><a href="#metrics" className="nav-link" onClick={() => handleOptionClick('metrics')}>Metrics</a></li>
          </ul>
        </div>
      </div>

      {/* Conditionally Render Content */}
      <main className="content p-3">
        {selectedOption === 'send-newsletter' && <NewsletterForm/>}
        {selectedOption === 'schedule-newsletter' && <NewsletterScheduledForm/>}
        {selectedOption === 'metrics' && <MetricsDashboard/>}
      </main>
    </div>
  );
}

export default App;
