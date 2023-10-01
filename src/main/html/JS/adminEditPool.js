// list pool
function showErrorMessagePoolList(codeError, bodyTag) {
    const h3 = document.createElement("h3");
    h3.textContent = "ERROR CODE: " + codeError + " - no data found";
    bodyTag.appendChild(h3);
}
function createTagWithoutTextAndAddAsChild(tagName, tagParent) {
    const tagCreated = document.createElement(tagName);
    tagParent.appendChild(tagCreated);
}
function createTagWithTextAndAddAsChild(tagName, tagTextContent, parentTag) {
    const newTag = document.createElement(tagName);
    newTag.textContent = tagTextContent;
    parentTag.appendChild(newTag);
}

function getPoolTag(pool) {
    const poolContent = document.createElement("article");

    createTagWithTextAndAddAsChild("h2",pool.name, poolContent);
    createTagWithTextAndAddAsChild("small","id: " + pool.id, poolContent);
    createTagWithoutTextAndAddAsChild("br", poolContent);
    createTagWithTextAndAddAsChild("span", "date de début: " + pool.startDate, poolContent);
    createTagWithoutTextAndAddAsChild("br", poolContent);
    createTagWithTextAndAddAsChild("span", "date de fin des test: " +pool.endDate, poolContent);
    createTagWithoutTextAndAddAsChild("br", poolContent);
    createTagWithTextAndAddAsChild("i", "à " + pool.location, poolContent);

    return poolContent;
}

function showPoolsOnStream(arrayData, bodyTag) {
    for (const pool of arrayData) {
        console.log(pool);
        const tag = getPoolTag(pool);
        bodyTag.appendChild(tag);
    }
}

function showAllPools() {
    ApiInteraction.getAllPools()
        .then((articleContent) => {
            const body = document.querySelector("#poolsList");
            if (typeof articleContent === "number") {
                showErrorMessagePoolList(articleContent, body);
            } else {
                showPoolsOnStream(articleContent, body);
            }
        });
}

// form functions
function getInputTagValueByName(name) {
    const tag = document.querySelector('input[name="' + name + '"]');
    return tag.value;
}

function showErrorMessagePoolForm(codeNumber) {
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
                    showErrorMessagePoolForm(JSON);
                } else {
                    window.location.href = "../htmlFile/listPiscine.html";
                }
            }
        );

    }
}


function createPoolListener() {
    const formTag = document.querySelector("#poolCreationForm button");
    formTag.addEventListener("click", createPoolOrModifyPool);
}

window.addEventListener("DOMContentLoaded", () => {
    showAllPools();
    createPoolListener();
});