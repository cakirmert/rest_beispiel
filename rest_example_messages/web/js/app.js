const BASE_URL = "/demo/rest/messages";

// ---------------------------- Event Listener

$("nav.new").addEventListener("click", function () {
    closeAllComponents();
    resetNewForm();
    openComponent("new-comp");
});

$("nav.all").addEventListener("click", function () {
    closeAllComponents();
    openComponent("all-comp");
    getMessage(BASE_URL, "all-comp.result");
});

$("nav.side-nav.new").addEventListener("click", function () {
    closeSideNav();
    closeAllComponents();
    resetNewForm();
    openComponent("new-comp");
});

$("nav.side-nav.all").addEventListener("click", function () {
    closeSideNav();
    closeAllComponents();
    openComponent("all-comp");
    getMessage(BASE_URL, "all-comp.result");
});

$("nav.hamburger").addEventListener("click", function () {
    openComponent("nav.side-nav");
    openComponent("nav.overlay");
});

$("nav.overlay").addEventListener("click", function () {
    closeSideNav();
});

$("new-comp.send-btn").addEventListener("click", function () {
    var text = $("new-comp.text").value;
    if (text) {
        postMessage(text);
    } else {
        $("new-comp.text").focus();
    }
});

$("new-comp.reset-btn").addEventListener("click", function () {
    resetNewForm();
});

$("detail-comp.update-btn").addEventListener("click", function () {
    var id = $("detail-comp.id").value;
    var text = $("detail-comp.text").value;
    if (text) {
        putMessage(text, id);
    } else {
        $("detail-comp.text").focus();
    }
});

$("detail-comp.delete-btn").addEventListener("click", function () {
    var id = $("detail-comp.id").value;
    deleteMessage(id);
});

$("detail-comp.back-btn").addEventListener("click", function () {
    closeComponent("detail-comp");
    openComponent("all-comp");
});

// ---------------------------- Funktionen mit Serverzugriff

function getMessage(url, id) {
    fetch(url, {
        method: "GET",
        headers: {
            "Accept": "application/json; charset=UTF-8"
        }
    }).then(response => {
        if (response.ok)
            return response.json();
        else
            throw new Error("HTTP-Statuscode = " + response.status);
    }).then(data => {
        parseJSON(data, id);
    }).catch(error => {
        alert(error.message);
    });
}

function postMessage(text) {
    var obj = {
        text: text
    };

    fetch(BASE_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(obj)
    }).then(response => {
        if (response.status == 201) {
            var location = response.headers.get("Location");
            getMessage(location, "new-comp.result");
        }
        else
            throw new Error("HTTP-Statuscode = " + response.status);
    }).catch(error => {
        alert(error.message);
    });
}

function fillForm(id) {
    fetch(BASE_URL + "/" + id, {
        method: "GET",
        headers: {
            "Accept": "application/json; charset=UTF-8"
        }
    }).then(response => {
        if (response.ok)
            return response.json();
        else
            throw new Error("HTTP-Statuscode = " + response.status);
    }).then(data => {
        $("detail-comp.id").value = data.id;
        $("detail-comp.text").value = data.text;
        $("detail-comp.timestamp").value = data.timestamp;
    }).catch(error => {
        alert(error.message);
    });
}

function putMessage(text, messageId) {
    var obj = {
        text: text
    };

    fetch(BASE_URL + "/" + messageId, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(obj)
    }).then(response => {
        if (response.ok) {
            closeComponent("detail-comp");
            openComponent("all-comp");
            getMessage(BASE_URL, "all-comp.result");
        }
        else
            throw new Error("HTTP-Statuscode = " + response.status);
    }).catch(error => {
        alert(error.message);
    });
}

function deleteMessage(messageId) {
    fetch(BASE_URL + "/" + messageId, {
        method: "DELETE"
    }).then(response => {
        if (response.ok) {
            closeComponent("detail-comp");
            openComponent("all-comp");
            getMessage(BASE_URL, "all-comp.result");
        }
        else
            throw new Error("HTTP-Statuscode = " + response.status);
    }).catch(error => {
        alert(error.message);
    });
}

// ---------------------------- Weitere Funktionen

function $(id) {
    return document.getElementById(id);
}

function closeSideNav() {
    closeComponent("nav.side-nav");
    closeComponent("nav.overlay");
}

function openComponent(id) {
    $(id).style.display = "block";
}

function closeComponent(id) {
    $(id).style.display = "none";
}

function closeAllComponents() {
    closeComponent("new-comp");
    closeComponent("all-comp");
    closeComponent("detail-comp");
}

function parseJSON(data, id) {
    $(id).innerHTML = "";

    if (Array.isArray(data)) {
        for (var val of data) {
            $(id).innerHTML +=
                `<a href="#" class="w3-btn w3-light-grey" onclick="showForm(${val.id})">${val.id}</a><br>
                    ${val.text}<br>${val.timestamp}<p>
                `;
        }
    } else {
        var html = data.id + "<br>";
        html += data.text + "<br>";
        html += data.timestamp + "<p>";
        $(id).innerHTML = html;
    }
}

function showForm(messageId) {
    closeAllComponents();
    openComponent("detail-comp");
    fillForm(messageId);
}

function resetNewForm() {
    $("new-comp.text").value = "";
    $("new-comp.result").innerHTML = "";
}
