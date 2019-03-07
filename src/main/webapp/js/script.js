/*  Modal delete employee  ------------------------------------------------------*/
$(document).ready(function () {
    const modal = $('#modalDeleteEmployee')

    modal.on('show.bs.modal', function (event) {
        let targetRow = $(event.relatedTarget).parent().parent()
        let modal = $(this)
        modal.find('.modal-delete-employee__id').text(targetRow.data('employee-id'))
        modal.find('.modal-delete-employee__firstname').text(targetRow.data('employee-firstname'))
        modal.find('.modal-delete-employee__lastname').text(targetRow.data('employee-lastname'))
    })

    $(document).ready(function () {
        modal.find('.modal-delete-employee__submit').click(function () {

            let id = modal.find('.modal-delete-employee__id').text()

            $.ajax({
                type: 'POST',
                url: 'delete-employee',
                async: false,
                data: 'id=' + id,
                success: function (response) {
                    $('#tr-employee-' + id).remove();
                    $('#modalDeleteEmployee').modal('hide')
                },
                error: function (e)//Если запрос не удачен
                {
                    // todo: make snackbar for error
                    alert("Запрос не удался!")
                }
            })
        })

    })
})


/*  Modal add employee ------------------------------------------------------*/
$(document).ready(function () {
    const modal = $('#modalAddEmployee')
    modal.find('.modal-add-employee__submit').click(function () {

        let jsonEmployee = JSON.stringify({
            firstName: modal.find('[name=firstName]').val(),
            lastName: modal.find('[name=lastName]').val(),
            departmentId: modal.find('[name=departmentId]').val()
        })


        $.ajax({
            type: 'POST',
            url: 'add-employee',
            async: false,
            dataType: "json",
            data: {
                employee: jsonEmployee
            },
            success: function (employee) {
                addEmployeeToTable(employee)
                $('#modalAddEmployee').modal('hide')
            }
            ,
            error: function (e)//Если запрос не удачен
            {
                // todo: make snackbar for error
                alert("Запрос не удался!")
            }
        })
    })


    function addEmployeeToTable(emp) {
        // get table body
        const tBody = document.querySelector('#employees tbody')

        // create table row
        const tr = document.createElement('tr')

        // create table data cells
        const tdEmpId = document.createElement('td');
        const tdEmpFirstName = document.createElement('td');
        const tdEmpLastName = document.createElement('td');
        const tdEmpDep = document.createElement('td');
        const tdActions = document.createElement('td');
        tdActions.classList.add('employee-table__actions')

        // create action buttons
        // super action button
        const actionBtn = document.createElement('button')
        const btnClasses = ['btn', 'btn-outline-secondary']
        // set attributes for the super action button
        actionBtn.setAttribute('type', 'button')
        actionBtn.classList.add(...btnClasses)
        actionBtn.dataset.toggle = 'modal'

        // create sub-buttons
        const delBtn = actionBtn.cloneNode(false)
        const editBtn = actionBtn.cloneNode(false)
        // set icons for the sub-buttons
        delBtn.innerHTML = '<i class="fas fa-trash"></i>'
        delBtn.classList.add('mr-2')
        delBtn.dataset.target = '#modalDeleteEmployee'

        editBtn.innerHTML = '<i class="fas fa-pencil-alt"></i>'
        editBtn.dataset.target = '#modalEditEmployee'

        // set attributes for the table row
        tr.setAttribute('id', 'tr-employee-' + emp.id);
        tr.dataset.employeeId = emp.id
        tr.dataset.employeeFirstname = emp.firstName
        tr.dataset.employeeLastname = emp.lastName
        tr.dataset.employeeDepartmentId = emp.department.id

        // set data to cells
        tdEmpId.innerText = emp.id
        tdEmpFirstName.innerText = emp.firstName
        tdEmpLastName.innerText = emp.lastName
        tdEmpDep.innerText = emp.department.name
        tdActions.appendChild(delBtn)
        tdActions.appendChild(editBtn)

        //set tds and tr into tbody
        tr.appendChild(tdEmpId)
        tr.appendChild(tdEmpFirstName)
        tr.appendChild(tdEmpLastName)
        tr.appendChild(tdEmpDep)
        tr.appendChild(tdActions)
        tBody.appendChild(tr)
    }
})


/*  Modal edit employee  ------------------------------------------------------*/
$(document).ready(function () {
    const modal = $('#modalEditEmployee')

    // show and fill the edit modal
    modal.on('show.bs.modal', function (event) {
        let targetRow = $(event.relatedTarget).parent().parent()
        let modal = $(this)
        modal.find('.modal-edit-employee__id').text(targetRow.data('employee-id'))
        modal.find('[name=id]').val(targetRow.data('employee-id'))
        modal.find('[name=firstName]').val(targetRow[0].dataset.employeeFirstname)
        modal.find('[name=lastName]').val(targetRow[0].dataset.employeeLastname)
        modal.find('[name=departmentId]').val(targetRow[0].dataset.employeeDepartmentId)
    })


    modal.find('.modal-edit-employee__submit').click(function () {

        let jsonEmployee = JSON.stringify({
            id: modal.find('[name=id]').val(),
            firstName: modal.find('[name=firstName]').val(),
            lastName: modal.find('[name=lastName]').val(),
            departmentId: modal.find('[name=departmentId]').val()
        })

        $.ajax({
            type: 'POST',
            url: 'update-employee',
            async: false,
            dataType: "json",
            data: {
                employee: jsonEmployee
            },
            success: function (employee) {
                updateEmployeeInTable(employee)
                $('#modalEditEmployee').modal('hide')
            }
            ,
            error: function (e)//Если запрос не удачен
            {
                // todo: make snackbar for error
                alert("Запрос не удался!")
            }
        })
    })

    function updateEmployeeInTable(emp) {
        const tr = document.querySelector('#tr-employee-' + emp.id)

        const tdList = tr.children
        tdList[1].innerText = emp.firstName
        tdList[2].innerText = emp.lastName
        tdList[3].innerText = emp.department.name

        // set attributes for the table row
        tr.setAttribute('id', 'tr-employee-' + emp.id);
        tr.dataset.employeeId = emp.id
        tr.dataset.employeeFirstname = emp.firstName
        tr.dataset.employeeLastname = emp.lastName
        tr.dataset.employeeDepartmentId = emp.department.id
    }
})