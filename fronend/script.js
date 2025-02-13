const API_URL = "http://localhost:8080/api";

// Session Management
function checkAuth() {
    const userId = localStorage.getItem("userId");
    if (userId) {
        document.getElementById("authSection").style.display = "none";
        document.getElementById("mainApp").style.display = "block";
        updatePatientSelects();
        fetchPatients();
    } else {
        document.getElementById("authSection").style.display = "block";
        document.getElementById("mainApp").style.display = "none";
    }
}

function logout() {
    localStorage.removeItem("userId");
    checkAuth();
}

// Register User
async function registerUser(event) {
    event.preventDefault();
    const msgElement = document.getElementById("registerMsg");
    
    try {
        const response = await fetch(`${API_URL}/users/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                email: document.getElementById("email").value,
                password: document.getElementById("password").value,
                name: document.getElementById("name").value
            })
        });

        if (!response.ok) throw new Error(await response.text());
        
        msgElement.style.color = "green";
        msgElement.innerText = "User Registered Successfully!";
        document.getElementById("registerForm").reset();
    } catch (error) {
        msgElement.style.color = "red";
        msgElement.innerText = `Error: ${error.message}`;
        console.error("Registration Error:", error);
    }
}

// User Login
async function loginUser(event) {
    event.preventDefault();
    const msgElement = document.getElementById("loginMsg");
    
    try {
        const response = await fetch(`${API_URL}/users/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                email: document.getElementById("loginEmail").value,
                password: document.getElementById("loginPassword").value
            })
        });

        if (!response.ok) throw new Error(await response.text());
        
        const data = await response.json();
        localStorage.setItem("userId", data.id);
        msgElement.style.color = "green";
        msgElement.innerText = "Login Successful!";
        document.getElementById("loginForm").reset();
        checkAuth();
    } catch (error) {
        msgElement.style.color = "red";
        msgElement.innerText = `Error: ${error.message}`;
        console.error("Login Error:", error);
    }
}

// Add Patient
async function addPatient(event) {
    event.preventDefault();
    const msgElement = document.getElementById("patientMsg");
    const userId = localStorage.getItem("userId");

    if (!userId) {
        msgElement.style.color = "red";
        msgElement.innerText = "Please login first";
        return;
    }

    try {
        const response = await fetch(`${API_URL}/patients`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                name: document.getElementById("patientName").value,
                age: document.getElementById("patientAge").value,
                user: { id: userId }
            })
        });

        if (!response.ok) throw new Error(await response.text());

        msgElement.style.color = "green";
        msgElement.innerText = "Patient Added Successfully!";
        document.getElementById("patientForm").reset();
        updatePatientSelects();
        fetchPatients();
    } catch (error) {
        msgElement.style.color = "red";
        msgElement.innerText = `Error: ${error.message}`;
        console.error("Patient Error:", error);
    }
}

// Fetch Patients
async function fetchPatients() {
    const userId = localStorage.getItem("userId");
    if (!userId) return;

    try {
        const response = await fetch(`${API_URL}/patients/user/${userId}`);
        if (!response.ok) throw new Error(await response.text());

        const data = await response.json();
        const list = document.getElementById("patientsList");
        list.innerHTML = "";
        
        data.forEach(patient => {
            const item = document.createElement("li");
            item.textContent = `Name: ${patient.name}, Age: ${patient.age}`;
            list.appendChild(item);
        });

        updatePatientSelects(data);
    } catch (error) {
        console.error("Error fetching patients:", error);
    }
}

// Update Patient Selects
function updatePatientSelects(patients) {
    const selects = ["patientId", "fetchPatientId"];
    selects.forEach(selectId => {
        const select = document.getElementById(selectId);
        select.innerHTML = '<option value="">Select Patient</option>';
        if (patients) {
            patients.forEach(patient => {
                const option = document.createElement("option");
                option.value = patient.id;
                option.textContent = patient.name;
                select.appendChild(option);
            });
        }
    });
}

// Add Heart Rate Record
async function addHeartRate(event) {
    event.preventDefault();
    const msgElement = document.getElementById("heartRateMsg");

    try {
        const response = await fetch(`${API_URL}/heartrate`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                bpm: document.getElementById("bpm").value,
                timestamp: document.getElementById("timestamp").value,
                patient: { id: document.getElementById("patientId").value }
            })
        });

        if (!response.ok) throw new Error(await response.text());

        msgElement.style.color = "green";
        msgElement.innerText = "Heart Rate Recorded Successfully!";
        document.getElementById("heartRateForm").reset();
        fetchHeartRates();
    } catch (error) {
        msgElement.style.color = "red";
        msgElement.innerText = `Error: ${error.message}`;
        console.error("Heart Rate Error:", error);
    }
}

// Fetch Heart Rate Records
async function fetchHeartRates() {
    const patientId = document.getElementById("fetchPatientId").value;
    if (!patientId) {
        document.getElementById("heartRateList").innerHTML = "";
        return;
    }

    try {
        const response = await fetch(`${API_URL}/heartrate/patient/${patientId}`);
        if (!response.ok) throw new Error(await response.text());

        const data = await response.json();
        const list = document.getElementById("heartRateList");
        list.innerHTML = "";

        if (data.length === 0) {
            list.innerHTML = "<p>No heart rate records found</p>";
            return;
        }

        data.forEach(record => {
            const item = document.createElement("div");
            const date = new Date(record.timestamp).toLocaleString();
            item.className = "heart-rate-record";
            item.innerHTML = `
                <strong>BPM:</strong> ${record.bpm}<br>
                <strong>Time:</strong> ${date}
            `;
            list.appendChild(item);
        });
    } catch (error) {
        console.error("Error fetching heart rates:", error);
        document.getElementById("heartRateList").innerHTML = 
            `<p style="color: red">Error loading heart rate records: ${error.message}</p>`;
    }
}

// Initialize the app
document.addEventListener("DOMContentLoaded", checkAuth);