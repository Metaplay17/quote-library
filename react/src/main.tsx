import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import { NotificationProvider } from './Modals/NotificationContext.tsx'
import RegistrationPage from './Pages/Registration/RegistrationPage.tsx'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import LoginPage from './Pages/Login/LoginPage.tsx'
import MainPage from './Pages/Main/MainPage.tsx'
import HomeBlock from './Pages/Home/HomeBlock.tsx'
import QuotesBlock from './Pages/Quotes/QuotesBlock.tsx'
import SavedQuotesBlock from './Pages/SavedQuotes/SavedQuotesBlock.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <NotificationProvider>
      <BrowserRouter>
        <Routes>
            <Route path="registration" element={<RegistrationPage />} />
            <Route path="login" element={<LoginPage />} />
            <Route path="home" element={<MainPage children={<HomeBlock />} />} />
            <Route path="quotes" element={<MainPage children={<QuotesBlock />} />} />
            <Route path="saved-quotes" element={<MainPage children={<SavedQuotesBlock />} />} />
        </Routes>
      </BrowserRouter>
    </NotificationProvider>
  </StrictMode>
)
