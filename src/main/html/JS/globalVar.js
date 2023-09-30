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

    get _adminPoolControllerUrl() {
        return this.adminURI + this.__POOL_CONTROLLER;
    },
    get _adminCandidateControllerUrl() {
        return this.adminURI + this.__CANDIDATE_CONTROLLER;
    },
    get _adminCandidateGroupControllerUrl() {
        return this.adminURI + this.__CANDIDATE_GROUP_CONTROLLER;
    },
    get _adminUserControllerUrl() {
        return this.adminURI + this.__USER_CONTROLLER;
    },
    get _adminCriteriaControllerUrl() {
        return this.adminURI + this.__CRITERIA_CONTROLLER;
    },
    get _getAdminCategoryControllerUrl() {
        return this.adminURI + this.__CATEGORY_CRITERIA_CONTROLLER;
    },


    _doRequest: async function (url, method) {
        const apiResponse = await fetch(url, method);
        if (apiResponse.ok) {
            return await apiResponse.json();
        } else {
            return apiResponse.status;
        }
    },
    _getRequestJsonContent: function (jsonContent, method) {
        const content = {
            method: method
        }
        if (jsonContent != null) {
            content["body"] = JSON.stringify(jsonContent);
            content["headers"] = {
                "Content-type": "application/json"
            };
        }

        return content
    },
    // _getRequestFormContent: function (formContent, method) {
    //
    // }


    getAllPools: async function () {
        const url = this._adminPoolControllerUrl;
        const requestContent = this._getRequestJsonContent(null, "GET");
        return await this._doRequest(url, requestContent);
    },
    getPool: async function (id) {
        const url = this._adminPoolControllerUrl + '/' + id;
        const requestContent = this._getRequestJsonContent(null, "GET");
        return await this._doRequest(url, requestContent);
    },
    createPool: async function (pool) {
        const url = this._adminPoolControllerUrl;
        const requestContent = this._getRequestJsonContent(pool, "POST");
        return await this._doRequest(url, requestContent);
    },
    modifyPool: async function (pool) {
        const url = this._adminPoolControllerUrl + '/' + pool.id;
        const requestContent = this._getRequestJsonContent(pool, "PUT");
        return await this._doRequest(url, requestContent);
    },

    getAllUser: async function () {
        const url = this._adminUserControllerUrl;
        const requestContent = this._getRequestJsonContent(null, "GET");
        return await this._doRequest(url, requestContent);
    },
    createUser: async function (user) {
        const url = this._adminUserControllerUrl;
        const requestContent = this._getRequestJsonContent(user, "POST");
        return await this._doRequest(url, requestContent);
    },

    createCriteria: async function (criteria) {
        const url = this._adminCriteriaControllerUrl;
        const requestContent = this._getRequestJsonContent(criteria, "POST");
        return await this._doRequest(url, requestContent);
    },
    getAllCriteria: async function () {
        const url = this._adminCriteriaControllerUrl;
        const requestContent = this._getRequestJsonContent(null, "GET");
        return await this._doRequest(url, requestContent);
    }


};


// console.log(ApiInteraction);
