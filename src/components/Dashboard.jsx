// src/components/Dashboard.jsx
import { useState, useEffect } from 'react';
import { dzieciAPI, kategorieAPI } from '../api/api';

function Dashboard({ user, onLogout }) {
  const [dzieci, setDzieci] = useState([]);
  const [kategorie, setKategorie] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [loading, setLoading] = useState(true);
  const [formData, setFormData] = useState({
    imie: '',
    nazwisko: '',
    dataUrodzenia: '',
    pesel: '',
    kategoriaId: ''
  });

  useEffect(() => {
    pobierzDane();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const pobierzDane = async () => {
    try {
      setLoading(true);
      const [dzieciData, kategorieData] = await Promise.all([
        dzieciAPI.pobierzDzieci(user.rodzicId),
        kategorieAPI.pobierzKategorie()
      ]);
      setDzieci(dzieciData || []);
      setKategorie(kategorieData || []);
    } catch (error) {
      console.error('Błąd pobierania danych:', error);
      alert('Błąd pobierania danych. Sprawdź czy backend działa na localhost:8080');
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async () => {
    try {
      const body = {
        imie: formData.imie,
        nazwisko: formData.nazwisko,
        dataUrodzenia: formData.dataUrodzenia,
        pesel: formData.pesel,
        kategoriaId: formData.kategoriaId ? Number(formData.kategoriaId) : null
      };
      await dzieciAPI.dodajDziecko(user.rodzicId, body);
      setShowModal(false);
      setFormData({ imie: '', nazwisko: '', dataUrodzenia: '', pesel: '', kategoriaId: '' });
      pobierzDane();
    } catch (error) {
      console.error(error);
      alert('Błąd dodawania dziecka');
    }
  };

  const handleUsun = async (dzieckoId) => {
    if (window.confirm('Czy na pewno chcesz usunąć dziecko?')) {
      try {
        await dzieciAPI.usunDziecko(dzieckoId, user.rodzicId);
        pobierzDane();
      } catch (error) {
        console.error(error);
        alert('Błąd usuwania dziecka');
      }
    }
  };

  const handleZmienKategorie = async (dzieckoId, kategoriaId) => {
    if (!kategoriaId) return;
    try {
      await dzieciAPI.przypiszKategorie(dzieckoId, kategoriaId, user.rodzicId);
      pobierzDane();
    } catch (error) {
      console.error(error);
      alert('Błąd zmiany kategorii');
    }
  };

  return (
    <div className="min-vh-100" style={{ backgroundColor: '#f8f9fa' }}>
      <nav className="navbar navbar-dark bg-success">
        <div className="container-fluid">
          <span className="navbar-brand mb-0 h1">⚽ LEGIA WARSZAWA - Akademia Piłkarska</span>
          <div className="d-flex align-items-center">
            <span className="text-white me-3">
              {user.imie} {user.nazwisko}
            </span>
            <button className="btn btn-outline-light btn-sm" onClick={onLogout}>
              Wyloguj
            </button>
          </div>
        </div>
      </nav>

      <div className="container py-4">
        {loading ? (
          <div className="text-center py-5">
            <div className="spinner-border text-success" role="status">
              <span className="visually-hidden">Ładowanie...</span>
            </div>
          </div>
        ) : (
          <>
            <div className="row mb-4">
              <div className="col">
                <h3>Moje dzieci</h3>
                <button 
                  className="btn btn-success" 
                  onClick={() => setShowModal(true)}
                >
                  + Dodaj dziecko
                </button>
              </div>
            </div>

            {dzieci.length === 0 ? (
              <div className="alert alert-info">
                Nie masz jeszcze dodanych dzieci. Kliknij "Dodaj dziecko" aby rozpocząć.
              </div>
            ) : (
              <div className="row">
                {dzieci.map((dziecko) => (
                  <div key={dziecko.id} className="col-md-6 col-lg-4 mb-4">
                    <div className="card shadow-sm h-100">
                      <div className="card-body">
                        <h5 className="card-title text-success">
                          {dziecko.imie} {dziecko.nazwisko}
                        </h5>
                        <p className="card-text">
                          <strong>Data urodzenia:</strong> {dziecko.dataUrodzenia}<br />
                          <strong>PESEL:</strong> {dziecko.pesel}
                        </p>
                        
                        <div className="mb-3">
                          <label className="form-label"><strong>Kategoria wiekowa:</strong></label>
                          <select
                            className="form-select form-select-sm"
                            value={dziecko.kategoriaId || ''}
                            onChange={(e) => handleZmienKategorie(dziecko.id, e.target.value)}
                          >
                            <option value="">Nie przypisano</option>
                            {kategorie.map((kat) => (
                              <option key={kat.id} value={kat.id}>
                                {kat.nazwa}
                              </option>
                            ))}
                          </select>
                        </div>

                        {dziecko.kategoriaNazwa && (
                          <div className="alert alert-success py-2 mb-3">
                            <small>✓ Zapisany do: {dziecko.kategoriaNazwa}</small>
                          </div>
                        )}

                        <button
                          className="btn btn-danger btn-sm w-100"
                          onClick={() => handleUsun(dziecko.id)}
                        >
                          Usuń dziecko
                        </button>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            )}

            <div className="card mt-4">
              <div className="card-body">
                <h5 className="card-title">Dostępne kategorie wiekowe</h5>
                <div className="table-responsive">
                  <table className="table">
                    <thead>
                      <tr>
                        <th>Kategoria</th>
                        <th>Roczniki</th>
                        <th>Opis</th>
                      </tr>
                    </thead>
                    <tbody>
                      {kategorie.map((kat) => (
                        <tr key={kat.id}>
                          <td><strong>{kat.nazwa}</strong></td>
                          <td>{kat.rokOd} - {kat.rokDo}</td>
                          <td>{kat.opis}</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </>
        )}
      </div>

      {showModal && (
        <div className="modal show d-block" tabIndex="-1" style={{ backgroundColor: 'rgba(0,0,0,0.5)' }}>
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Dodaj dziecko</h5>
                <button 
                  type="button" 
                  className="btn-close" 
                  onClick={() => setShowModal(false)}
                ></button>
              </div>
              <div className="modal-body">
                <div className="mb-3">
                  <label className="form-label">Imię</label>
                  <input
                    type="text"
                    className="form-control"
                    name="imie"
                    value={formData.imie}
                    onChange={handleChange}
                  />
                </div>

                <div className="mb-3">
                  <label className="form-label">Nazwisko</label>
                  <input
                    type="text"
                    className="form-control"
                    name="nazwisko"
                    value={formData.nazwisko}
                    onChange={handleChange}
                  />
                </div>

                <div className="mb-3">
                  <label className="form-label">Data urodzenia</label>
                  <input
                    type="date"
                    className="form-control"
                    name="dataUrodzenia"
                    value={formData.dataUrodzenia}
                    onChange={handleChange}
                  />
                </div>

                <div className="mb-3">
                  <label className="form-label">PESEL</label>
                  <input
                    type="text"
                    className="form-control"
                    name="pesel"
                    maxLength="11"
                    value={formData.pesel}
                    onChange={handleChange}
                  />
                </div>

                <div className="mb-3">
                  <label className="form-label">Kategoria wiekowa (opcjonalnie)</label>
                  <select
                    className="form-select"
                    name="kategoriaId"
                    value={formData.kategoriaId}
                    onChange={handleChange}
                  >
                    <option value="">Wybierz kategorię</option>
                    {kategorie.map((kat) => (
                      <option key={kat.id} value={kat.id}>
                        {kat.nazwa}
                      </option>
                    ))}
                  </select>
                </div>
              </div>
              <div className="modal-footer">
                <button 
                  className="btn btn-secondary" 
                  onClick={() => setShowModal(false)}
                >
                  Anuluj
                </button>
                <button 
                  className="btn btn-success"
                  onClick={handleSubmit}
                >
                  Dodaj
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default Dashboard;
