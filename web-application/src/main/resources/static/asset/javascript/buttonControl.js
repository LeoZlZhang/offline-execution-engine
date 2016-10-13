function loadTestDataFromServer(sourceFileName) {
    var json = null;
    $.ajax({
        'url': '/ee/v1/testcase/getbysourcefilename',
        'data': {
            'name': sourceFileName
        },
        'type': 'get',
        'contentType': "application/json",
        'dataType': 'json',
        'async': false,
        success: function (data) {
            json = data.result;
        },
        error: function (e) {
            alert("fail to get test data from backend" + e);
        }
    });
    return json;
}

function saveTestData(testData) {
    $.ajax({
        'url': '/ee/v1/testcase/save',
        'data': JSON.stringify(testData),
        'type': 'post',
        'contentType': "application/json",
        'dataType': 'json',
        'async': false,
        error: function (e) {
            console.log("fail to get test data from backend" + e);
        }
    });
}



function deleteTestData(sourceFileName) {
    $.ajax({
        'url': '/ee/v1/testcase/deletebysourcefilename',
        'data': {
            'name': sourceFileName
        },
        'type': 'delete',
        'contentType': "application/json",
        'dataType': 'json',
        'async': false,
        error: function (e) {
            console.log("fail to get test data from backend" + e);
        }
    });
}



$('#btn_save').on('click', function (e, data) {
    var treeIns = $('#tree').jstree(true);
    var node = treeIns.get_selected(true);
    if (node.length > 0 && node[0].icon === 'jstree-file') {
        var testDataName = node[0].text;
        var jsonData = editor.get();
        jsonData.sourceFileName = testDataName;
        editor.set(jsonData);
        saveTestData(jsonData);
    } else
        alert('Can not save, please select a test case from catalog!')
});