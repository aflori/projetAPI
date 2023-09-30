function showErrorMessage(codeError, bodyTag) {
    const h1 = document.createElement("h1");
    h1.textContent = "ERROR CODE: " + codeError + " - no data found";
    bodyTag.appendChild(h1);
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
        // console.log(pool);
        const tag = getPoolTag(pool);
        bodyTag.appendChild(tag);
    }
}

function showAllPools() {
    ApiInteraction.getAllPools()
        .then((articleContent) => {
            const body = document.body;
            if (typeof articleContent === "number") {
                showErrorMessage(articleContent, body);
            } else {
                showPoolsOnStream(articleContent, body);
            }
        });
}


window.addEventListener("DOMContentLoaded", (event) => {
    showAllPools();
})