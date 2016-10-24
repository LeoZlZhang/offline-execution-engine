function loadCatalog() {
    var catalog = {};
    $.ajax({
        'url': '/ee/v1/catalog/load',
        'type': 'get',
        'contentType': "application/json",
        'dataType': 'json',
        'async': false,
        success: function (data) {
            catalog = data.result;
        },
        error: function (e) {
            console.log("fail to get catalog from backend" + e);
        }
    });
    return catalog;
}


function saveCatalogData(catalogData) {
    $.ajax({
        'url': '/ee/v1/catalog/save',
        'data': JSON.stringify(catalogData),
        'type': 'post',
        'contentType': "application/json",
        'dataType': 'json',
        'async': false,
        error: function (e) {
            console.log("fail to save catalogJson data to backend" + e);
        }
    });
}


function getApiData(apiData) {
    var json = {};
    $.ajax({
        'url': '/ee/v1/data/get?' + jQuery.param(apiData),
        'type': 'get',
        'contentType': "application/json",
        'dataType': 'json',
        'async': false,
        success: function (data) {
            if (data.result && data.result.length > 0)
                json = data.result[0];
        },
        error: function () {
            json = false;
        }
    });

    return json;
}

function saveTestData(testData) {
    $.ajax({
        'url': '/ee/v1/data/save',
        'data': JSON.stringify(testData),
        'type': 'post',
        'contentType': "application/json",
        'dataType': 'json',
        'async': false,
        success: function (data) {
            testData = data.result;
        },
        error: function () {
            testData = false;
        }
    });

    return testData;
}


function deleteTestData(apiTestData) {
    var result = true;

    $.ajax({
        'url': '/ee/v1/data/delete',
        'data': JSON.stringify(apiTestData),
        'type': 'delete',
        'contentType': "application/json",
        'dataType': 'json',
        'async': false,
        error: function () {
            result = false;
        }
    });

    return result;
}


function executeByData(testData, gearId, profileId) {
    var json = {};
    $.ajax({
        'url': '/ee/v1/execution/go?' + jQuery.param({gearId: gearId, profileId: profileId}),
        'data': JSON.stringify(testData),
        'type': 'post',
        'contentType': "application/json",
        'dataType': 'json',
        'async': false,
        success: function (data) {
            if (data.result && data.result.length > 0)
                json = data.result[0];
        },
        error: function () {
            json = false;
        }
    });
    return json;
}


function loadAllGear() {
    var gears = {};
    $.ajax({
        'url': '/ee/v1/gear/load',
        'type': 'get',
        'contentType': "application/json",
        'dataType': 'json',
        'async': false,
        success: function (data) {
            if (data.status && data.result)
                gears = data.result;
            else
                console.log("fail to load gear from backend");
        },
        error: function (e) {
            console.log("fail to load gear from backend " + e);
        }
    });
    return gears;
}


function loadAllProfile() {
    var profiles = {};
    $.ajax({
        'url': '/ee/v1/profile/load',
        'type': 'get',
        'contentType': "application/json",
        'dataType': 'json',
        'async': false,
        success: function (data) {
            if (data.status && data.result)
                profiles = data.result;
            else
                console.log("fail to load profile from backend");
        },
        error: function (e) {
            console.log("fail to load profile from backend " + e);
        }
    });
    return profiles;
}

