import React from 'react';
import './css/Notifications.css';

interface NotificationAlertProps {
  isOpen: boolean;
  title: string;
  message: string;
  onOk: () => void;
}

const NotificationAlert: React.FC<NotificationAlertProps> = ({ 
  isOpen,
  title,
  message,
  onOk
}) => {
  if (!isOpen) return null;

  return (
    <div className="notification-overlay">
      <div className="notification-modal">
        <h3>{title}</h3>
        <p>{message}</p>
        <div className="notification-actions">
          <button onClick={onOk}>OK</button>
        </div>
      </div>
    </div>
  );
};

export default NotificationAlert;