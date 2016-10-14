$('#btn_save').on('click', function (e, data) {
    if (lastSelectedTestData != null) {
        var jsonData = editor.get();
        jsonData.sourceFileName = lastSelectedTestData.text;
        editor.set(jsonData);
        jsonData.id = lastSelectedTestData.id;
        jsonData = saveTestData(jsonData);
        lastSelectedTestData.id = jsonData.id;
    } else
        alert('Can not save, please select a test case from catalog!')
});