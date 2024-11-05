// ScheduledEmailsTable.js
import React, { useEffect, useState } from 'react';
import { Table, Button, Alert, Spinner } from 'react-bootstrap';
import axios from 'axios';

function ScheduledEmailsTable() {
  const [emails, setEmails] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [message, setMessage] = useState(null);

  
  const fetchScheduledEmails = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await axios.get('http://127.0.0.1:8080/api/v1/newsletter/scheduled'); 
      setEmails(response.data);
    } catch (err) {
      setError("Failed to load scheduled emails.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchScheduledEmails();
  }, []);

  const handleButtonClick = async (id) => {
    try {
      await axios.post(`http://127.0.0.1:8080/api/v1/newsletter/schedule/cancel?id=${id}`); 
      setMessage(`This schedule is cancelled now`);
      fetchScheduledEmails();
    } catch (err) {
      setError(`Failed to cancel this schedule`);
    }
  };

  return (
    <div>
      <h2>Scheduled Newsletter</h2>
      
      {loading && <Spinner animation="border" />}

      {error && <Alert variant="danger">{error}</Alert>}

      {message && <Alert variant="success">{message}</Alert>}

      <Table striped bordered hover className="mt-3">
        <thead>
          <tr>
            <th>Subject</th>
            <th>Scheduled Time</th>
            <th>Recipients</th>
            <th>Documents</th>
            <th>Cancel</th>
          </tr>
        </thead>
        <tbody>
          {emails.map((email) => (
            <tr key={email.id}>
              <td>{email.subject}</td>
              <td>{new Date(email.scheduledTime).toLocaleString()}</td>
              <td>
                {email.recipients.map((recipient, index) => (
                  <div key={index}>{recipient.email}</div>
                ))}
              </td>
              <td>
                {email.documents.length > 0 ? (
                  email.documents.map((doc, index) => (
                    <div key={index}>
                      <a href={doc.url} target="_blank" rel="noopener noreferrer">
                        {doc.fileName}
                      </a>
                    </div>
                  ))
                ) : (
                  <div>No documents</div>
                )}
              </td>
              <td>
                <Button variant="primary" onClick={() => handleButtonClick(email.id)}>
                  Cancel Schedule
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
}

export default ScheduledEmailsTable;
