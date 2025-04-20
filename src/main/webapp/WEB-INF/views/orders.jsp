<c:forEach var="order" items="${orders}">
  <!-- Твої поля: id, дата, статус... -->

  <tr>
    <td colspan="5">
      <strong>Позиції замовлення:</strong>
      <table border="1">
        <tr>
          <th>Товар</th>
          <th>Кількість</th>
        </tr>
        <c:forEach var="item" items="${order.items}">
          <tr>
            <td>${item.product.name}</td>
            <td>${item.quantity}</td>
          </tr>
        </c:forEach>
      </table>
    </td>
  </tr>
</c:forEach>
