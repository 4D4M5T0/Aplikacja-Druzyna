import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

const unwrap = (promise) => promise.then(res => res.data);

export const authAPI = {
  rejestracja: dane => unwrap(api.post('/auth/rejestracja', dane)),
  logowanie: dane => unwrap(api.post('/auth/logowanie', dane)),
};

export const dzieciAPI = {
  pobierzDzieci: rodzicId => unwrap(api.get(`/dzieci/rodzic/${rodzicId}`)),
  dodajDziecko: (rodzicId, dane) => unwrap(api.post(`/dzieci/rodzic/${rodzicId}`, dane)),
  usunDziecko: (dzieckoId, rodzicId) => unwrap(api.delete(`/dzieci/${dzieckoId}/rodzic/${rodzicId}`)),
  przypiszKategorie: (dzieckoId, kategoriaId, rodzicId) =>
    unwrap(api.put(`/dzieci/${dzieckoId}/kategoria/${kategoriaId}/rodzic/${rodzicId}`)),
};

export const kategorieAPI = {
  pobierzKategorie: () => unwrap(api.get('/kategorie')),
};

export default api;
