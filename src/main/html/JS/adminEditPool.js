function getInputTagValueByName(name) {
    const tag = document.querySelector('input[name="' + name + '"]');
    return tag.value;
}

function showErrorMessage(codeNumber) {
    console.log("Impossible de créer la piscine, code d'erreur : " + codeNumber);
}
function createPool(event) {
    event.preventDefault();
    const pool = {}

    const id = getInputTagValueByName("id");
    if (id !== "") {
        pool.id = id;
    }

    pool.name = getInputTagValueByName("name");
    pool.startDate = getInputTagValueByName("startDate");
    pool.endDate = getInputTagValueByName("endDate");
    pool.location = getInputTagValueByName("location");
    ApiInteraction.createPool(pool).then(
        (JSON) => {
            if (typeof JSON === "number") {
                showErrorMessage(JSON);
            }
            else {
                window.location.href = "../htmlFile/administrationPiscine.html";
            }
        }
    );
}


function createPoolListener() {
    const formTag = document.querySelector("button");
    formTag.addEventListener("click", createPool);
}

window.addEventListener("DOMContentLoaded", createPoolListener);