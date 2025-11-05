// src/components/Logowanie.jsx
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { authAPI } from '../api/api';

function Logowanie({ onLogin }) {
  const [email, setEmail] = useState('');
  const [haslo, setHaslo] = useState('');
  const [blad, setBlad] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setBlad('');
    setLoading(true);

    try {
      const data = await authAPI.logowanie({ email, haslo }); // data to AuthResponse
      // zapisz w localStorage i podnieś stan w App
      onLogin(data);
      navigate('/dashboard');
    } catch (error) {
      setBlad('Nieprawidłowy email lub hasło');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-vh-100 d-flex align-items-center justify-content-center bg-light">
      <div className="container">
        <div className="row justify-content-center">
          <div className="col-md-6 col-lg-4">
            <div className="card shadow">
              <div className="card-body p-4">
                <div className="text-center mb-4">
                  <h2 className="text-success fw-bold">LEGIA WARSZAWA</h2>
                  <p className="text-muted">System zapisów do akademii</p>
                </div>

                {blad && (
                  <div className="alert alert-danger" role="alert">
                    {blad}
                  </div>
                )}

                <form onSubmit={handleSubmit}>
                  <div className="mb-3">
                    <label className="form-label">Email</label>
                    <input
                      type="email"
                      className="form-control"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                      disabled={loading}
                    />
                  </div>

                  <div className="mb-3">
                    <label className="form-label">Hasło</label>
                    <input
                      type="password"
                      className="form-control"
                      value={haslo}
                      onChange={(e) => setHaslo(e.target.value)}
                      disabled={loading}
                    />
                  </div>

                  <button 
                    className="btn btn-success w-100 mb-3"
                    type="submit"
                    disabled={loading || !email || !haslo}
                  >
                    {loading ? 'Logowanie...' : 'Zaloguj się'}
                  </button>
                </form>

                <div className="text-center">
                  <Link to="/rejestracja" className="btn btn-link text-decoration-none">
                    Nie masz konta? Zarejestruj się
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

export default Logowanie;
