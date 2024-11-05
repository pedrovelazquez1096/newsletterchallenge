import React, { useState } from 'react';
import { Container, Form, Button, Row, Col, Modal } from 'react-bootstrap';
import { useDropzone } from 'react-dropzone';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

function NewsletterScheduledForm() {
  // State management
  const [emails, setEmails] = useState([]);
  const [newEmail, setNewEmail] = useState('');
  const [subject, setSubject] = useState('');
  const [files, setFiles] = useState([]);
  const [selectedDate, setSelectedDate] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [removedFileName, setRemovedFileName] = useState('');

  // Handle file drop
  const onDrop = (acceptedFiles) => {
    setFiles([...files, ...acceptedFiles]);
  };
  const { getRootProps, getInputProps } = useDropzone({ onDrop });

  // Handle adding a new email
  const addEmail = () => {
    if (newEmail && !emails.includes(newEmail)) {
      setEmails([...emails, newEmail]);
      setNewEmail('');
    }
  };

  // Handle removing an email
  const removeEmail = (emailToRemove) => {
    setEmails(emails.filter((email) => email !== emailToRemove));
  };

  // Handle file removal
  const removeFile = (fileName) => {
    setFiles(files.filter((file) => file.name !== fileName));
    setRemovedFileName(fileName);
    setShowModal(true);
  };

  // Close modal
  const closeModal = () => setShowModal(false);

  return (
    <Container className="mt-4">
      <h2>Schedule a Newsletter</h2>

      {/* Drag-and-Drop File Input */}
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

      {/* Email Input */}
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

      {/* Date and Time Picker */}
      <Form.Group className="mb-3">
        <Form.Label>Select Date and Time</Form.Label>
        <DatePicker
          selected={selectedDate}
          onChange={(date) => setSelectedDate(date)}
          showTimeSelect
          timeFormat="HH:mm"
          timeIntervals={15}
          dateFormat="MMMM d, yyyy h:mm aa"
          className="form-control"
        />
      </Form.Group>

      {/* Subject Input */}
      <Form.Group className="mb-3">
        <Form.Label>Subject</Form.Label>
        <Form.Control
          type="text"
          placeholder="Enter subject"
          value={subject}
          onChange={(e) => setSubject(e.target.value)}
        />
      </Form.Group>

      {/* Submit Button */}
      <Button variant="primary" type="submit">
        Send Email
      </Button>

      {/* Modal Notification */}
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

// Custom styles
const dropzoneStyle = {
  border: '2px dashed #cccccc',
  borderRadius: '5px',
  padding: '20px',
  textAlign: 'center',
  cursor: 'pointer',
  color: '#999999',
};