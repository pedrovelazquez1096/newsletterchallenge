import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import NewsletterScheduledForm from './NewsletterScheduledForm';
import NewsletterForm from './NewsletterForm';
import MetricsDashboard from './MetricsDashboard';
import ScheduledEmailsTable from './ScheduledEmailsTable'

function App() {
  const [isOpen, setIsOpen] = useState(false);
  const [selectedOption, setSelectedOption] = useState('');

  const toggleSidebar = () => {
    setIsOpen(!isOpen);
  };

  const handleOptionClick = (option) => {
    setSelectedOption(option);
    toggleSidebar(); 
  };

  return (
    <div className="App d-flex">
      <nav className="navbar navbar-dark bg-dark w-100">
        <button className="btn btn-dark" onClick={toggleSidebar}>
          ☰
        </button>
        <h1 className="text-light mx-3">My App</h1>
      </nav>

      <div className={`offcanvas offcanvas-start ${isOpen ? 'show' : ''}`} tabIndex="-1" style={{ visibility: isOpen ? 'visible' : 'hidden' }}>
        <div className="offcanvas-header">
          <h5 className="offcanvas-title">Menu</h5>
          <button type="button" className="btn-close text-reset" onClick={toggleSidebar}></button>
        </div>
        <div className="offcanvas-body">
          <ul className="list-unstyled">
            <li><a href="#send-newsletter" className="nav-link" onClick={() => handleOptionClick('send-newsletter')}>Send Newsletter</a></li>
            <li><a href="#schedule-newsletter" className="nav-link" onClick={() => handleOptionClick('schedule-newsletter')}>Schedule Newsletter</a></li>
            <li><a href="#cancel-schedules" className="nav-link" onClick={() => handleOptionClick('cancel-schedules')}>Cancel Schedule Newsletter</a></li>
            <li><a href="#metrics" className="nav-link" onClick={() => handleOptionClick('metrics')}>Metrics</a></li>
          </ul>
        </div>
      </div>

      <main className="content p-3">
        {selectedOption === 'send-newsletter' && <NewsletterForm/>}
        {selectedOption === 'schedule-newsletter' && <NewsletterScheduledForm/>}
        {selectedOption === 'cancel-schedules' && <ScheduledEmailsTable/>}
        {selectedOption === 'metrics' && <MetricsDashboard/>}
      </main>
    </div>
  );
}

export default App;
