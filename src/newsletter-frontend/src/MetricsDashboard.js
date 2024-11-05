import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import   
 { faEnvelope, faCalendarCheck, faCalendarTimes, faUserGroup, faUserCheck, faUserTimes } from '@fortawesome/free-solid-svg-icons';

function MetricsDashboard() {
  const [metrics, setMetrics] = useState({
    mailsSent: 0,
    mailsScheduled: 0,
    mailsCancelled: 0,
    totalRecipients: 0,
    recipientsSubscribed: 0,
    recipientsUnsubscribed: 0,
  });

  useEffect(() => {
    const fetchMetrics = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/v1/metrics');
        setMetrics(response.data);
      } catch (error) {
        console.error('Error fetching metrics:', error);
        // Handle errors, e.g., display an error message to the user
      }
    };
  
    fetchMetrics();
  }, []);

  return (
    <div className="metrics-dashboard">
      <h2>Metrics Dashboard</h2>
      <div className="metric">
        <FontAwesomeIcon icon={faEnvelope} />
        <p>Mails Sent:</p>
        <p>{metrics.mailsSent}</p>
      </div>
      <div className="metric">
        <FontAwesomeIcon icon={faCalendarCheck} />
        <p>Mails Scheduled:</p>
        <p>{metrics.mailsScheduled}</p>
      </div>
      <div className="metric">
        <FontAwesomeIcon icon={faCalendarTimes} />
        <p>Mails Cancelled:</p>
        <p>{metrics.mailsCancelled}</p>
      </div>
      <div className="metric">
        <FontAwesomeIcon icon={faUserGroup} />
        <p>Total Recipients:</p>
        <p>{metrics.totalRecipients}</p>
      </div>
      <div className="metric">
        <FontAwesomeIcon icon={faUserCheck} />
        <p>Recipients Subscribed:</p>
        <p>{metrics.recipientsSubscribed}</p>
      </div>
      <div className="metric">
        <FontAwesomeIcon icon={faUserTimes} />
        <p>Recipients Unsubscribed:</p>
        <p>{metrics.recipientsUnsubscribed}</p>
      </div>
    </div>
  );
}

export default MetricsDashboard;