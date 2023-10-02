/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Lucas
 */
public class script {
    document.addEventListener("DOMContentLoaded", function () {
    const listaProveedores = document.getElementById("listaProveedores");
    const mostrarGasistasButton = document.getElementById("mostrarGasistas");

    // Supongamos que tienes una lista de proveedores en formato JSON
    const proveedores = [
        { nombre: "Proveedor 1", tipo: "gasista" },
        { nombre: "Proveedor 2", tipo: "electricista" },
        { nombre: "Proveedor 3", tipo: "gasista" },
        // Agrega más proveedores aquí
    ];

    // Función para mostrar solo los gasistas
    function mostrarGasistas() {
        // Limpia la lista
        listaProveedores.innerHTML = "";

        // Filtra los proveedores que son gasistas
        const gasistas = proveedores.filter(proveedor => proveedor.tipo === "gasista");

        // Crea elementos de lista para los gasistas
        gasistas.forEach(gasista => {
            const listItem = document.createElement("li");
            listItem.textContent = gasista.nombre;
            listaProveedores.appendChild(listItem);
        });
    }

    // Asocia la función de mostrarGasistas al botón
    mostrarGasistasButton.addEventListener("click", mostrarGasistas);
});

}
