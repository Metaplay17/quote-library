import React from 'react';
import './css/Notifications.css';

interface NotificationConfirmProps {
  isOpen: boolean;
  title: string;
  message: string;
  onYes: () => void;
  onNo: () => void;
}

const NotificationConfirm: React.FC<NotificationConfirmProps> = ({ 
  isOpen,
  title,
  message,
  onYes,
  onNo
}) => {
  if (!isOpen) return null;

  return (
    <div className="notification-overlay">
      <div className="notification-modal">
        <h3>{title}</h3>
        <p>{message}</p>
        <div className="notification-actions">
          <button onClick={onNo}>Нет</button>
          <button onClick={onYes} className="btn-yes">Да</button>
        </div>
      </div>
    </div>
  );
};

export default NotificationConfirm;