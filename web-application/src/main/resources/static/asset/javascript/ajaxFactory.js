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
        'url': '/ee/v1/data/get',
        'data': apiData,
        'type': 'get',
        'contentType': "application/json",
        'dataType': 'json',
        'async': false,
        success: function (data) {
            if (data.result)
                json = data.result[0];
            delete json.id;
        },
        error: function (e) {
            alert("fail to get test data from backend" + e);
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
        error: function (e) {
            console.log("fail to get test data from backend" + e);
        }
    });

    return testData;
}


function deleteTestData(apiTestData) {
    $.ajax({
        'url': '/ee/v1/data/delete',
        'data': JSON.stringify(apiTestData),
        'type': 'delete',
        'contentType': "application/json",
        'dataType': 'json',
        'async': false,
        error: function (e) {
            console.log("fail to get test data from backend" + e);
        }
    });
}