import React, { useEffect, useState } from 'react';
import { Container, Form, Button, Row, Col, Modal } from 'react-bootstrap';
import { useDropzone } from 'react-dropzone';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import axios from 'axios';

function NewsletterScheduledForm() {
  const [emails, setEmails] = useState([]);
  const [newEmail, setNewEmail] = useState('');
  const [subject, setSubject] = useState('');
  const [files, setFiles] = useState([]);
  const [selectedDate, setSelectedDate] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [removedFileName, setRemovedFileName] = useState('');


  const onDrop = (acceptedFiles) => {
    setFiles([...files, ...acceptedFiles]);
  };
  const { getRootProps, getInputProps } = useDropzone({ onDrop });
  useEffect(() => {
    console.log(selectedDate);
  }, [selectedDate]);

  
  const addEmail = () => {
    if (newEmail && !emails.includes(newEmail)) {
      setEmails([...emails, newEmail]);
      setNewEmail('');
    }
  };

  const removeEmail = (emailToRemove) => {
    setEmails(emails.filter((email) => email !== emailToRemove));
  };

  const removeFile = (fileName) => {
    setFiles(files.filter((file) => file.name !== fileName));
    setRemovedFileName(fileName);
    setShowModal(true);
  };

  const sendNewsletter = async () => {
    if (!emails.length || !subject) {
        alert('Please enter recipient emails and a subject!');
        return;
    }

    const uploadPromises = files.map(async (file) => {
        console.log("uploading file");
        console.log(file);
        const formData = new FormData();
        
        formData.append('document', file);

        try {
            const response = await axios.post('http://127.0.0.1:8080/api/v1/documents', formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
            });
            console.log("ID: ");
            console.log(response.data);
            return response.data;
        } catch (error) {
            console.error('Error uploading file:', error);
            return null;
        }
        });
        
    const uploadedIds = await Promise.all(uploadPromises);

    try {
        const response = await axios.post('http://127.0.0.1:8080/api/v1/newsletter/schedule', {
            emails,
            subject,
            scheduleTime: selectedDate,
            documents: uploadedIds, 
        });
    
        console.log('Email sent successfully:', response.data);
        resetForm(); 
    } catch (error) {
        console.error('Error sending email:', error);
    }
  }

  const resetForm = () => {
    setEmails([]);
    setNewEmail('');
    setSubject('');
    setFiles([]);
  };

  
  const closeModal = () => setShowModal(false);

  return (
    <Container className="mt-4">
      <h2>Schedule a Newsletter</h2>

      <Form.Group className="mb-3">
        <Form.Label>Attach Files</Form.Label>
        <div {...getRootProps({ className: 'dropzone' })} style={dropzoneStyle}>
          <input {...getInputProps()} />
          <p>Drag & drop files here, or click to select files</p>
        </div>
        {files.length > 0 && (
          <ul className="list-unstyled mt-2">
            {files.map((file, index) => (
              <li key={index} className="d-flex justify-content-between align-items-center">
                {file.name}
                <Button variant="danger" size="sm" onClick={() => removeFile(file.name)}>
                  Remove
                </Button>
              </li>
            ))}
          </ul>
        )}
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Recipient Emails</Form.Label>
        <Row>
          <Col xs={9}>
            <Form.Control
              type="email"
              placeholder="Enter email"
              value={newEmail}
              onChange={(e) => setNewEmail(e.target.value)}
            />
          </Col>
          <Col xs={3}>
            <Button variant="primary" onClick={addEmail} disabled={!newEmail}>
              Add Email
            </Button>
          </Col>
        </Row>
        <ul className="list-unstyled mt-2">
          {emails.map((email, index) => (
            <li key={index} className="d-flex justify-content-between align-items-center">
              {email}
              <Button variant="danger" size="sm" onClick={() => removeEmail(email)}>
                Remove
              </Button>
            </li>
          ))}
        </ul>
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Select Date and Time</Form.Label>
        <DatePicker
          selected={selectedDate}
          onChange={(date) => setSelectedDate(date)}
          showTimeSelect
          timeFormat="HH:mm"
          timeIntervals={1}
          dateFormat="MMMM d, yyyy h:mm aa"
          className="form-control"
        />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Subject</Form.Label>
        <Form.Control
          type="text"
          placeholder="Enter subject"
          value={subject}
          onChange={(e) => setSubject(e.target.value)}
        />
      </Form.Group>

      <Button variant="primary" type="submit" onClick={sendNewsletter}>
        Send Email
      </Button>

      <Modal show={showModal} onHide={closeModal}>
        <Modal.Header closeButton>
          <Modal.Title>File Removed</Modal.Title>
        </Modal.Header>
        <Modal.Body>{removedFileName} has been removed from the attachment list.</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={closeModal}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
}

export default NewsletterScheduledForm;

const dropzoneStyle = {
  border: '2px dashed #cccccc',
  borderRadius: '5px',
  padding: '20px',
  textAlign: 'center',
  cursor: 'pointer',
  color: '#999999',
};