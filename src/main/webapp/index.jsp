<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" %>

<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <a class="navbar-brand" href="#">Departments</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault"
            aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="#">Link</a>
            </li>

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">Dropdown</a>
                <div class="dropdown-menu" aria-labelledby="dropdown01">
                    <a class="dropdown-item" href="#">Action</a>
                    <a class="dropdown-item" href="#">Another action</a>
                    <a class="dropdown-item" href="#">Something else here</a>
                </div>
            </li>
        </ul>
    </div>
</nav>

<main role="main" class="container pt-5">
    <div class="row">
        <h2>Common table</h2>
        <div class="table-responsive">
            <table class="table table-striped table-sm mb-5">
                <thead>
                <tr>
                    <th>department id</th>
                    <th>department name</th>
                    <th>parent department</th>
                    <th>employee first name</th>
                    <th>employee second name</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>1</td>
                    <td>Lorem</td>
                    <td>ipsum</td>
                    <td>dolor</td>
                    <td>sit</td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>amet</td>
                    <td>consectetur</td>
                    <td>adipiscing</td>
                    <td>elit</td>
                </tr>
                <tr>
                    <td>3</td>
                    <td>Integer</td>
                    <td>nec</td>
                    <td>odio</td>
                    <td>Praesent</td>
                </tr>
                <tr>
                    <td>4</td>
                    <td>libero</td>
                    <td>Sed</td>
                    <td>cursus</td>
                    <td>ante</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

    <div class="row justify-content-between">
        <button type="button" class="btn btn-outline-primary btn-lg">
            Add department
            <i class="fas fa-plus"></i>
        </button>
        <button type="button" class="btn btn-outline-primary btn-lg">
            Add employee
            <i class="fas fa-plus"></i>
        </button>
    </div>
</main>
