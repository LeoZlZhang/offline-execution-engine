$('#btn_load').on('click', function (e, data) {
    if (lastSelectedTestData) {

        var jsonData = getApiData({id: lastSelectedTestData.id});

        if (jsonData === false) {
            alert('request server fail to load test data!');
        } else {
            delete jsonData.id;
            editor.set(jsonData);
            if (myCallBack && myCallBack instanceof Function)
                myCallBack();
        }

    } else {
        alert('Can not load, please select a test case from catalog!')
    }
});


$('#btn_save').on('click', function (e, data) {
    if (lastSelectedTestData) {
        var jsonData = editor.get();

        if (lastSelectedTestData.id.indexOf('_') < 0)
            jsonData.id = lastSelectedTestData.id;

        jsonData.sourceFileName = lastSelectedTestData.text;

        jsonData = saveTestData(jsonData);

        if (jsonData === false) {
            alert('request server fail to save test data!');
        } else {
            var treeinst = $('#tree').jstree(true)
            treeinst.set_id(lastSelectedTestData, jsonData.id);
            var catalog = treeinst.get_json('#', {flat: false});
            saveCatalogData(catalog);
            delete jsonData.id;
            editor.set(jsonData);
        }

    } else
        alert('Can not save, please select a test case from catalog!')
});


$('#btn_delete').on('click', function (e, data) {
    if (lastSelectedTestData) {
        var childnode = lastSelectedTestData.children_d;
        for (var ai in childnode) {
            var result = deleteTestData({id: childnode[ai]});

            if (result === false) {
                alert("request server fail to delete test data");
            }
        }
        result = deleteTestData({id: lastSelectedTestData.id});

        if (result === false) {
            alert("request server fail to delete test data");
        }

        editor.set({});
        lastSelectedTestData = null;
    }
});



$('#btn_execute').on('click', function (e, data) {
    if (lastSelectedTestData) {

    }
});
