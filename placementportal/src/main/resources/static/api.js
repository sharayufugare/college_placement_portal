const BASE_URL = "http://localhost:8080";

// ---- LOGIN ----
async function loginUser(data) {
    const res = await fetch(`${BASE_URL}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    });
    return res.json();
}

// ---- GET STUDENT DASHBOARD ----
async function getStudent(id, token) {
    const res = await fetch(`${BASE_URL}/student/dashboard/${id}`, {
        headers: { "Authorization": "Bearer " + token }
    });
    return res.json();
}

// ---- UPDATE STUDENT ----
async function updateStudent(id, data, token) {
    const res = await fetch(`${BASE_URL}/student/update/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify(data)
    });
    return res.json();
}

// ---- UPLOAD RESUME ----
async function uploadResume(id, file, token) {
    let formData = new FormData();
    formData.append("file", file);

    const res = await fetch(`${BASE_URL}/student/uploadResume/${id}`, {
        method: "POST",
        headers: { "Authorization": "Bearer " + token },
        body: formData
    });
    return res.text();
}

async function getCompanies() {
    let res = await fetch("http://localhost:8080/api/company/all");
    return await res.json();
}



// ---- UPLOAD PHOTO ----
async function uploadPhoto(id, file, token) {
    let formData = new FormData();
    formData.append("file", file);

    const res = await fetch(`${BASE_URL}/student/uploadPhoto/${id}`, {
        method: "POST",
        headers: { "Authorization": "Bearer " + token },
        body: formData
    });
    return res.text();
}
//-----nayaaa
// static/api.js
// Small helper used by dashboard and login pages.

async function apiFetch(url, options = {}) {
    // attach JWT if available
    const token = localStorage.getItem('token');
    const headers = options.headers || {};
    if (token) headers['Authorization'] = `Bearer ${token}`;

    // default JSON headers for body if we send JSON
    if (options.body && !(options.body instanceof FormData) && !headers['Content-Type']) {
        headers['Content-Type'] = 'application/json';
    }

    const res = await fetch(url, { ...options, headers });
    // try parse JSON, but not required
    let text = await res.text();
    let data;
    try { data = text ? JSON.parse(text) : null; } catch (e) { data = text; }
    return { ok: res.ok, status: res.status, data, rawText: text };
}

async function getCompanies() {
    const r = await apiFetch('/api/company/all');
    if (r.ok) return Array.isArray(r.data) ? r.data : [];
    console.error('Failed to fetch companies', r);
    return [];
}

async function getStudent(studentId) {
    if (!studentId) return null;
    const r = await apiFetch(`/student/dashboard/${studentId}`);
    if (r.ok) return r.data;
    console.error('Failed to fetch student', r);
    return null;
}

async function applyToCompany(studentId, companyId) {
    // best-effort: try POST to /student/apply (backend optional)
    const payload = { studentId, companyId };
    const r = await apiFetch('/student/apply', { method: 'POST', body: JSON.stringify(payload) });
    return r;
}
