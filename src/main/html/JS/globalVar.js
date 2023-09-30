// holder of all data to connect to API
const ApiInteraction = {
    __API_ADDRESS: "http://localhost:8080/api",
    __ADMIN_API: "/admin",
    __OBSERVER_API: "/observer",

    __POOL_CONTROLLER: "/pool",
    __CANDIDATE_CONTROLLER: "/candidate",
    __CANDIDATE_GROUP_CONTROLLER: "/group",
    __USER_CONTROLLER: "/observer",
    __CRITERIA_CONTROLLER: "/criteria",
    __CATEGORY_CRITERIA_CONTROLLER: "/category",


    get adminURI() {
        return this.__API_ADDRESS + this.__ADMIN_API;
    },
    get observerURI() {
        return this.__API_ADDRESS + this.__OBSERVER_API;
    },

    get _getAdminPoolControllerUrl () {
        return this.adminURI + this.__POOL_CONTROLLER;
    },
    get _getAdminCandidateControllerUrl () {
        return this.adminURI + this.__CANDIDATE_CONTROLLER;
    },
    get _getAdminCandidateGroupControllerUrl () {
        return this.adminURI + this.__CANDIDATE_GROUP_CONTROLLER;
    },
    get _getAdminUserControllerUrl () {
        return this.adminURI + this.__USER_CONTROLLER;
    },
    get _getAdminCriteriaControllerUrl () {
        return this.adminURI + this.__CRITERIA_CONTROLLER;
    },
    get _getAdminCategoryControllerUrl () {
        return this.adminURI + this.__CATEGORY_CRITERIA_CONTROLLER;
    },


    _doRequest: async function (url, method) {
        const apiResponse = await fetch(url, method);
        if (apiResponse.ok) {
            return await apiResponse.json();
        } else {
            return {
                error: "request failed",
                http_code: apiResponse.status
            };
        }
    },

    _getRequestContent: function (pool, method) {
        const content = {
            method: method
        }
        if (pool != null) {
            content["body"] = JSON.stringify(pool);
            content["headers"] = {
                "Content-type": "application/json"
            };
        }

        return content
    },

    getAllPools: async function () {
        const url = this._getAdminPoolControllerUrl();
        const requestContent = this._getRequestContent(null, "GET");
        return await this._doRequest(url, requestContent);
    },

    getPool: async function (id) {
        const url = this._getAdminPoolControllerUrl() + '/' + id;
        const requestContent = this._getRequestContent(null, "GET");
        return await this._doRequest(url, requestContent);
    },

    createPool: async function (pool) {
        const url = this._getAdminPoolControllerUrl();
        const requestContent = this._getRequestContent(pool, "POST");
        return await this._doRequest(url, requestContent);
    },

    modifyPool: async function (pool) {
        const url = this._getAdminPoolControllerUrl() + '/' + pool.id;
        const requestContent = this._getRequestContent(pool, "PUT");
        return await this._doRequest(url, requestContent);
    },
};


console.log(ApiInteraction);
