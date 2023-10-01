function getInputTagValueByName(name) {
    const tag = document.querySelector('input[name="' + name + '"]');
    return tag.value;
}

function showErrorMessage(codeNumber) {
    console.log("Impossible de créer la piscine, code d'erreur : " + codeNumber);
}

function hasNoEmptyMandatoryField(pool) {
    return pool.name !== "" && pool.startDate !== "" && pool.endDate !== "" && pool.location !== "";
}

function createPoolOrModifyPool(event) {
    event.preventDefault();
    const pool = {}
    let isModifyingAPool = false;

    const id = getInputTagValueByName("id");
    if (id !== "") {
        pool.id = id;
        isModifyingAPool = true;
    }

    pool.name = getInputTagValueByName("name");
    pool.startDate = getInputTagValueByName("startDate");
    pool.endDate = getInputTagValueByName("endDate");
    pool.location = getInputTagValueByName("location");

    if (hasNoEmptyMandatoryField(pool)) {
        let responseValue;

        if (isModifyingAPool) {
            responseValue = ApiInteraction.createPool(pool);
        } else {
            responseValue = ApiInteraction.modifyPool(pool);
        }

        responseValue.then(
            (JSON) => {
                if (typeof JSON === "number") {
                    showErrorMessage(JSON);
                } else {
                    window.location.href = "../htmlFile/listPiscine.html";
                }
            }
        );

    }
}


function createPoolListener() {
    const formTag = document.querySelector("button[type=submit]");
    formTag.addEventListener("click", createPoolOrModifyPool);
}

window.addEventListener("DOMContentLoaded", createPoolListener);