//-----------------------------------------------------------GENERAL----------------------------------------------------------------

const APIURL = "http://localhost:9010";

// Called on every page, redirect the user to the login page if they are not logged in
function redirect() {
	if (sessionStorage.getItem("token") != null) {

	}
	else {
		window.location.href = "./login.html";
	}
}

// Delete the token, logging the user off
function deleteToken() {
	sessionStorage.removeItem("token");
	sessionStorage.removeItem("email");
	redirect();
}

// Call the needed functions at the load of the home page
function loadHome() {
	redirect();
	getBalance();
	getHomeBalance();
	displayHomeTransfer();
}

// Call the needed functions at the load of the transfer page
function loadTransfer() {
	redirect();
	displayTransfer();
	displayTransferContact();
	getBalance();
}

// Call the needed functions at the load of the profile page
function loadProfile() {
	redirect();
	displayProfile();
	getBalance();
}

// Call the needed functions at the load of the contact page
function loadContact() {
	redirect();
	displayContact();
	getBalance();
}

//------------------------------------------------------------LOGIN-----------------------------------------------------------------

// Use the informations to validate the credentials of the user on the API end
async function login() {
	let loginData =
	{
		username: document.getElementById("email").value,
		password: document.getElementById("password").value
	};
	const response = await fetch(APIURL + "/login",
		{
			method: "POST",
			headers:
			{
				"Content-Type": "application/json",
			},
			body: JSON.stringify(loginData)
		});

	const data = await response.json();
	sessionStorage.setItem("token", data.token);
	sessionStorage.setItem("email", loginData.username);
	window.location.href = "./home.html";

}
//-----------------------------------------------------------TRANSFER----------------------------------------------------------------

// Main function for getting the list of transaction from the API
async function getTransfer() {
	let response = await fetch(APIURL + "/transaction", {
		method: 'GET',
		headers: {
			'Authorization': 'Bearer ' + sessionStorage.getItem("token"),
			'Content-Type': 'application/json'
		}
	})
		.catch(error => {
			console.error('Erreur:', error);
		});
	return response.json();
}

// Secondary function managing the display of transactions in the transfer page
async function displayTransfer() {
	let transfers = await getTransfer();
	const table = document.getElementById("transfer");
	const user = await getUser(sessionStorage.getItem("email"));
	const appBalance = document.getElementById("appBalance");
	let generatedMoney = Number(0);

	if (user.admin) {
		let transferHeader = document.getElementById("transferHeader");
		transferHeader.innerHTML = "<th scope=\"col\">Sender</th><th scope=\"col\">Receiver</th><th scope=\"col\">Description</th><th scope=\"col\">Amount</th>"
		for (let i = 0; i < transfers.length; i++) {
			let line = document.createElement("tr")
			line.id = "line" + i;
			line.innerHTML = "<td>" + transfers[i].sender.email + "</td> <td>" + transfers[i].receiver.email + "</td> <td>" + transfers[i].description + "</td> <td>" + transfers[i].amount + " €</td>";
			table.appendChild(line);
			generatedMoney = +(generatedMoney + +(transfers[i].amount / 100 * transfers[i].fee).toFixed(2)).toFixed(2);
		}
		appBalance.hidden = false;
		appBalance.innerHTML = "Generated Money : " + generatedMoney + "€";
	}
	else {
		for (let i = 0; i < transfers.length; i++) {
			if (transfers[i].receiver.email == sessionStorage.getItem("email")) {
				let line = document.createElement("tr")
				line.id = "line" + i;
				line.innerHTML = "<td>" + transfers[i].sender.email + "</td> <td>" + transfers[i].description + "</td> <td>+ " + (Math.round((transfers[i].amount - (transfers[i].amount / 100.0 * transfers[i].fee)) * 100) / 100) + " €</td>";
				table.appendChild(line);
			}
			else {
				let line = document.createElement("tr")
				line.id = "line" + i;
				line.innerHTML = "<td>" + transfers[i].receiver.email + "</td> <td>" + transfers[i].description + "</td> <td>- " + transfers[i].amount + " €</td>";
				table.appendChild(line);
			}
		}
	}

}

// Secondary function managing the display of transactions in the home page
async function displayHomeTransfer() {
	let transfers = await getTransfer();
	const table = document.getElementById("lastTransactions");

	for (let i = 0; i < transfers.length && i < 3; i++) {
		if (transfers[i].receiver.email == sessionStorage.getItem("email")) {
			let line = document.createElement("tr")
			line.id = "line" + i;
			line.innerHTML = "<td>" + transfers[i].sender.email + "</td> <td>+ " + (Math.round((transfers[i].amount - (transfers[i].amount / 100.0 * transfers[i].fee)) * 100) / 100) + " €</td>";
			table.appendChild(line);
		}
		else {
			let line = document.createElement("tr")
			line.id = "line" + i;
			line.innerHTML = "<td>" + transfers[i].receiver.email + "</td> <td>- " + transfers[i].amount + " €</td>";
			table.appendChild(line);
		}
	}
}

// Main function to perform a transaction
async function pay(targetEmail, amount, description) {
	if (document.getElementById("errorMessage")) {
		document.getElementById("errorMessage").remove();
	}
	let user1 = await getUser(sessionStorage.getItem("email"));
	if (amount >= user1.solde) {
		let sendMoney = document.getElementById("sendMoney");
		let errorMessage = document.createElement("div");
		errorMessage.id = "errorMessage";
		errorMessage.className = "alert alert-danger";
		errorMessage.innerHTML = "Incorrect amount"
		sendMoney.appendChild(errorMessage);
	}
	else if (!targetEmail.includes("@")) {
		let sendMoney = document.getElementById("sendMoney");
		let errorMessage = document.createElement("div");
		errorMessage.id = "errorMessage";
		errorMessage.className = "alert alert-danger";
		errorMessage.innerHTML = "No contact selected"
		sendMoney.appendChild(errorMessage);
	}
	else if (document.getElementById("description").value == "") {
		let sendMoney = document.getElementById("sendMoney");
		let errorMessage = document.createElement("div");
		errorMessage.id = "errorMessage";
		errorMessage.className = "alert alert-danger";
		errorMessage.innerHTML = "Description is empty"
		sendMoney.appendChild(errorMessage);
	}
	else {
		let user2 = await getUser(targetEmail);
		const fee = 0.5;
		const date = Date.now();
		const transaction = { "sender": user1, "receiver": user2, amount, fee, description, date };
		await postTransation(transaction);
		location.reload();
	}
}

//Secondary function to execute the request of a transaction
async function postTransation(transaction) {
	let response = await fetch(APIURL + "/transaction", {
		method: 'POST',
		headers: {
			'Authorization': 'Bearer ' + sessionStorage.getItem("token"),
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(transaction)
	});
	return response;
}

async function reloadAccount(amount)
{
	let user = await getUser(sessionStorage.getItem("email"));
	user.solde = +user.solde + +amount;
	let response = await fetch(APIURL + "/user", {
		method: 'PATCH',
		headers: {
			'Authorization': 'Bearer ' + sessionStorage.getItem("token"),
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(user)
	});
	location.reload();
	return response;
}

//-----------------------------------------------------------PROFILE----------------------------------------------------------------

async function displayProfile() {
	let user = await getUser(sessionStorage.getItem("email"));
	let email = document.getElementById("email");
	let balance = document.getElementById("infoBalance");
	email.innerHTML = user.email;
	balance.innerHTML = user.solde + "€";
}

//-----------------------------------------------------------CONTACT----------------------------------------------------------------

// Main function for getting the list of contacts from the API for the page contact
async function getContact() {
	let response = await fetch(APIURL + "/contact", {
		method: 'GET',
		headers: {
			'Authorization': 'Bearer ' + sessionStorage.getItem("token"),
			'Content-Type': 'application/json'
		}
	})
		.catch(error => {
			console.error('Erreur:', error);
		});
	return response.json();
}

// Secondary function managing the display of contacts in the contact page
async function displayContact() {
	let contacts = await getContact();
	const table = document.getElementById("contactList");

	for (let i = 0; i < contacts.length; i++) {
		if (contacts[i].user1.email == sessionStorage.getItem("email")) {
			let line = document.createElement("tr")
			line.id = "line" + i;
			line.innerHTML = "<td>" + contacts[i].user2.email + "</td> <td><button class=\"btn btn-danger btn-sm\" onclick=\"removeContact(\'" + contacts[i].user2.email + "\')\">Delete</button></td>";
			table.appendChild(line);
		}
		else {
			let line = document.createElement("tr")
			line.id = "line" + i;
			line.innerHTML = "<td>" + contacts[i].user1.email + "</td> <td><button class=\"btn btn-danger btn-sm\" onclick=\"removeContact(\'" + contacts[i].user1.email + "\')\">Delete</button></td>";
			table.appendChild(line);
		}
	}
}

// Secondary function managing the display of contacts in the transfer page
async function displayTransferContact() {
	let contacts = await getContact();
	const connexions = document.getElementById("contact");

	for (let i = 0; i < contacts.length; i++) {
		let contact = document.createElement("option")
		contact.id = "contact" + i;
		if (contacts[i].user1.email == sessionStorage.getItem("email")) {
			contact.innerHTML = contacts[i].user2.email;
		}
		else {
			contact.innerHTML = contacts[i].user1.email;
		}
		connexions.appendChild(contact);
	}
}


// Create a contact based on the email of the target user
async function addContact(userEmail) {
	let user1 = await getUser(sessionStorage.getItem("email"));
	let user2 = await getUser(userEmail);
	let users = { user1, user2 };
	await postContact(users);
	location.reload();
}

// Secondary function used to post the user once the data is gathered
async function postContact(users) {
	await fetch(APIURL + "/contact", {
		method: 'POST',
		headers: {
			'Authorization': 'Bearer ' + sessionStorage.getItem("token"),
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(users)
	})
		.then(response => {
			if (!response.ok) {
				throw new Error('Erreur lors de la récupération des données');
			}
			return response.json();
		})
		.then(data => {
			// Traitement des données récupérées
			//console.log(data);
		})
		.catch(error => {
			console.error('Erreur:', error);
		});
}

// Delete a contact based on the email of the target user
async function removeContact(userEmail) {
	let user1 = await getUser(sessionStorage.getItem("email"));
	let user2 = await getUser(userEmail);
	let users = { user1, user2 };
	await deleteContact(users);
	location.reload();
}

// Secondary function used to post the user once the data is gathered
async function deleteContact(users) {
	await fetch(APIURL + "/contact", {
		method: 'DELETE',
		headers: {
			'Authorization': 'Bearer ' + sessionStorage.getItem("token"),
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(users)
	})
		.then(response => {
			if (!response.ok) {
				throw new Error('Erreur lors de la récupération des données');
			}
			return response.json();
		})
		.then(data => {
			// Traitement des données récupérées
			//console.log(data);
		})
		.catch(error => {
			console.error('Erreur:', error);
		});
}

//-----------------------------------------------------------DIVERS----------------------------------------------------------------
// Get the balance of the user
async function getBalance() {
	let response = await fetch(APIURL + "/user?email=" + sessionStorage.getItem("email"), {
		method: 'GET',
		headers: {
			'Authorization': 'Bearer ' + sessionStorage.getItem("token"),
			'Content-Type': 'application/json'
		}
	})
		.catch(error => {
			console.error('Erreur:', error);
		});


	response = await response.json();
	let balance = document.getElementById("balance");
	balance.innerHTML = ("Solde : " + response.solde + "€");
	return response;
}

// Get the balance of the user for the Home page
async function getHomeBalance() {
	let response = await fetch(APIURL + "/user?email=" + sessionStorage.getItem("email"), {
		method: 'GET',
		headers: {
			'Authorization': 'Bearer ' + sessionStorage.getItem("token"),
			'Content-Type': 'application/json'
		}
	})
		.catch(error => {
			console.error('Erreur:', error);
		});


	response = await response.json();
	let balance = document.getElementById("homeBalance");
	balance.innerHTML = ("Solde : " + response.solde + "€");
	return response;
}

// Get an user based on an email
async function getUser(userEmail) {
	let response = await fetch(APIURL + "/user?email=" + userEmail, {
		method: 'GET',
		headers: {
			'Authorization': 'Bearer ' + sessionStorage.getItem("token"),
			'Content-Type': 'application/json'
		}
	})
		.catch(error => {
			console.error('Erreur:', error);
		});

	return response.json();
}