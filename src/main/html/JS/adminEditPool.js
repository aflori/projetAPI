//generic function
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

function createImgTagWithPictureAndAddAsChild(photoName, alt, parentTag) {
    const img = document.createElement("img");

    //provisory solution waiting to see how it works on Linux OS
    img.src = '../../../../assets/candidatePhoto/' + decodeURIComponent(photoName);
    img.alt = alt;
    parentTag.appendChild(img);
}

// list pool
function getPoolTag(pool) {
    const poolContent = document.createElement("article");

    createTagWithTextAndAddAsChild("h2", pool.name, poolContent);
    createTagWithTextAndAddAsChild("small", "id: " + pool.id, poolContent);
    createTagWithoutTextAndAddAsChild("br", poolContent);
    createTagWithTextAndAddAsChild("span", "date de début: " + pool.startDate, poolContent);
    createTagWithoutTextAndAddAsChild("br", poolContent);
    createTagWithTextAndAddAsChild("span", "date de fin des test: " + pool.endDate, poolContent);
    createTagWithoutTextAndAddAsChild("br", poolContent);
    createTagWithTextAndAddAsChild("i", "à " + pool.location, poolContent);

    return poolContent;
}

function showPoolsOnStream(arrayData, bodyTag) {
    for (const pool of arrayData) {
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

//criteria listing
function showErrorMessageCriteriaList(codeError) {
    console.log("impossible de charger les critères");
    console.log("code d'erreur: " + codeError + ".");
}

function createCriteriaTag(criteria) {
    const tag = document.createElement("article");
    createTagWithTextAndAddAsChild("h3", criteria.name, tag);
    createTagWithoutTextAndAddAsChild("br", tag);
    createTagWithTextAndAddAsChild("small", "id = " + criteria.id, tag);
    createTagWithoutTextAndAddAsChild("br", tag);
    createTagWithTextAndAddAsChild("p", criteria.description, tag);
    return tag;
}

function showCriteriaOnScreen(criterias) {
    const parentTag = document.querySelector("#criteriaList");
    for (const criteria of criterias) {
        const tagCriteria = createCriteriaTag(criteria);
        parentTag.appendChild(tagCriteria);
    }
}

function showAllCriterias() {
    ApiInteraction.getAllCriteria().then((criterias) => {
        if (typeof criterias === "number") {
            showErrorMessageCriteriaList(criterias);
        } else {
            showCriteriaOnScreen(criterias);
        }
    })
}

//observer listing
function showErrorMessageObserverList(codeError) {
    console.log("impossible de charger la liste des observateurs");
    console.log("code d'erreur: " + codeError + ".");
}

function createObserverTag(observer) {
    const tag = document.createElement("article");
    createTagWithTextAndAddAsChild("h3", observer.firstName + ' ' + observer.lastName, tag);
    createTagWithoutTextAndAddAsChild("br", tag);
    createTagWithTextAndAddAsChild("small", "id = " + observer.id, tag);
    createTagWithoutTextAndAddAsChild("br", tag);
    createTagWithTextAndAddAsChild("p", "email : " + observer.email, tag);
    return tag;
}

function showObserverOnScreen(observerList) {
    const parentTag = document.querySelector("#observerList");
    for (const observer of observerList) {
        const tagObserver = createObserverTag(observer);
        parentTag.appendChild(tagObserver);
    }
}

function showAllObservers() {
    ApiInteraction.getAllUser().then((observerList) => {
        if (typeof observerList === "number") {
            showErrorMessageObserverList(observerList);
        } else {
            showObserverOnScreen(observerList);
        }
    })
}


//candidate listing
function showErrorMessageCandidateList(codeError) {
    console.log("impossible de charger la liste des candidats");
    console.log("code d'erreur: " + codeError + ".");
}

function createCandidateTag(candidate) {

    const candidateFullName = candidate.firstName + ' ' + candidate.lastName;
    const tag = document.createElement("article");

    createTagWithTextAndAddAsChild("h3", candidateFullName, tag);
    createTagWithoutTextAndAddAsChild("br", tag);
    createTagWithTextAndAddAsChild("small", "id = " + candidate.id, tag);
    createTagWithoutTextAndAddAsChild("br", tag);
    createImgTagWithPictureAndAddAsChild(candidate.photoName, candidateFullName, tag);

    return tag;
}

function showCandidateOnScreen(candidateList) {
    const parentTag = document.querySelector("#candidateList");
    for (const candidate of candidateList) {
        const tagObserver = createCandidateTag(candidate);
        parentTag.appendChild(tagObserver);
    }
}

function showAllCandidate() {
    ApiInteraction.getAllCandidate().then((observerList) => {
        if (typeof observerList === "number") {
            showErrorMessageCandidateList(observerList);
        } else {
            showCandidateOnScreen(observerList);
        }
    })
}

window.addEventListener("DOMContentLoaded", () => {
    showAllPools();
    createPoolListener();
    showAllCriterias();

    showAllObservers();

    showAllCandidate();

});