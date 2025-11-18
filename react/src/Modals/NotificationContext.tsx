import React, { createContext, useContext, useState, type ReactNode } from 'react';
import NotificationAlert from './NotificationAlert';
import NotificationConfirm from './NotificationConfirm';

interface NotificationContextProps {
  showAlert: (options: {
    title: string;
    message: string;
    onOk?: () => void;
  }) => void;
  showConfirm: (options: {
    title: string;
    message: string;
    onYes?: () => void;
    onNo?: () => void;
  }) => void;
}

const NotificationContext = createContext<NotificationContextProps | undefined>(undefined);

export const useNotificationDialog = () => {
  const context = useContext(NotificationContext);
  if (!context) {
    throw new Error('useNotificationDialog must be used within a NotificationProvider');
  }
  return context;
};

interface NotificationProviderProps {
  children: ReactNode;
}

export const NotificationProvider: React.FC<NotificationProviderProps> = ({ children }) => {
  const [alertState, setAlertState] = useState<{ isOpen: boolean; title: string; message: string; onOk?: () => void } | null>(null);
  const [confirmState, setConfirmState] = useState<{ isOpen: boolean; title: string; message: string; onYes?: () => void; onNo?: () => void } | null>(null);

  const showAlert = (options: { title: string; message: string; onOk?: () => void }) => {
    setAlertState({ ...options, isOpen: true });
  };

  const showConfirm = (options: { title: string; message: string; onYes?: () => void; onNo?: () => void }) => {
    setConfirmState({ ...options, isOpen: true });
  };

  const closeAlert = () => {
    setAlertState(null);
  };

  const closeConfirm = () => {
    setConfirmState(null);
  };

  const handleAlertOk = () => {
    alertState?.onOk?.();
    closeAlert();
  };

  const handleConfirmYes = () => {
    confirmState?.onYes?.();
    closeConfirm();
  };

  const handleConfirmNo = () => {
    confirmState?.onNo?.();
    closeConfirm();
  };

  return (
    <NotificationContext.Provider value={{ showAlert, showConfirm }}>
      {children}
      {alertState && (
        <NotificationAlert
          isOpen={alertState.isOpen}
          title={alertState.title}
          message={alertState.message}
          onOk={handleAlertOk}
        />
      )}
      {confirmState && (
        <NotificationConfirm
          isOpen={confirmState.isOpen}
          title={confirmState.title}
          message={confirmState.message}
          onYes={handleConfirmYes}
          onNo={handleConfirmNo}
        />
      )}
    </NotificationContext.Provider>
  );
};