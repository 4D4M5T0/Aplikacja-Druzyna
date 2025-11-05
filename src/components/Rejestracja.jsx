// src/components/Rejestracja.jsx
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { authAPI } from '../api/api';

function Rejestracja({ onLogin }) {
  const [formData, setFormData] = useState({
    email: '',
    haslo: '',
    potwierdzHaslo: '',
    imie: '',
    nazwisko: '',
    telefon: ''
  });
  const [blad, setBlad] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async () => {
    setBlad('');

    if (formData.haslo !== formData.potwierdzHaslo) {
      setBlad('Hasła nie są zgodne');
      return;
    }

    if (formData.haslo.length < 6) {
      setBlad('Hasło musi mieć minimum 6 znaków');
      return;
    }

    setLoading(true);

    try {
      const { potwierdzHaslo, ...dataToSend } = formData;
      const data = await authAPI.rejestracja(dataToSend); // data to AuthResponse
      onLogin(data);
      navigate('/dashboard');
    } catch (error) {
      setBlad('Rejestracja nie powiodła się. Email może być już zajęty.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-vh-100 d-flex align-items-center justify-content-center bg-light">
      <div className="container">
        <div className="row justify-content-center">
          <div className="col-md-8 col-lg-6">
            <div className="card shadow">
              <div className="card-body p-4">
                <div className="text-center mb-4">
                  <h2 className="text-success fw-bold">LEGIA WARSZAWA</h2>
                  <p className="text-muted">Rejestracja rodzica</p>
                </div>

                {blad && (
                  <div className="alert alert-danger" role="alert">
                    {blad}
                  </div>
                )}

                <div className="row">
                  <div className="col-md-6 mb-3">
                    <label className="form-label">Imię</label>
                    <input
                      type="text"
                      className="form-control"
                      name="imie"
                      value={formData.imie}
                      onChange={handleChange}
                      disabled={loading}
                    />
                  </div>

                  <div className="col-md-6 mb-3">
                    <label className="form-label">Nazwisko</label>
                    <input
                      type="text"
                      className="form-control"
                      name="nazwisko"
                      value={formData.nazwisko}
                      onChange={handleChange}
                      disabled={loading}
                    />
                  </div>
                </div>

                <div className="mb-3">
                  <label className="form-label">Email</label>
                  <input
                    type="email"
                    className="form-control"
                    name="email"
                    value={formData.email}
                    onChange={handleChange}
                    disabled={loading}
                  />
                </div>

                <div className="mb-3">
                  <label className="form-label">Telefon</label>
                  <input
                    type="tel"
                    className="form-control"
                    name="telefon"
                    value={formData.telefon}
                    onChange={handleChange}
                    disabled={loading}
                  />
                </div>

                <div className="row">
                  <div className="col-md-6 mb-3">
                    <label className="form-label">Hasło</label>
                    <input
                      type="password"
                      className="form-control"
                      name="haslo"
                      value={formData.haslo}
                      onChange={handleChange}
                      disabled={loading}
                    />
                  </div>

                  <div className="col-md-6 mb-3">
                    <label className="form-label">Potwierdź hasło</label>
                    <input
                      type="password"
                      className="form-control"
                      name="potwierdzHaslo"
                      value={formData.potwierdzHaslo}
                      onChange={handleChange}
                      disabled={loading}
                    />
                  </div>
                </div>

                <button 
                  className="btn btn-success w-100 mb-3"
                  onClick={handleSubmit}
                  disabled={loading}
                >
                  {loading ? 'Rejestracja...' : 'Zarejestruj się'}
                </button>

                <div className="text-center">
                  <Link to="/logowanie" className="btn btn-link text-decoration-none">
                    Masz już konto? Zaloguj się
                  </Link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div> 
    </div>
  );
}

export default Rejestracja;
