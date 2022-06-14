# Spring Exercise - Internet Programming Course

## Authors

Shaked Vaknin - shakedva@edu.hac.ac.il </br>
Noy Tal - noyta@edu.hac.ac.il

## General Information

This is a book store created with Spring-Boot and Thymeleaf. <br/>
The shopping cart is implemented with session beans. <br/>

There are 2 zones in this website
<ol>
<li>Admin Zone</li>
    <ul>
    <li>Admin zone is protected with spring security</li>
    <li>Add new books to the store</li>
    <li>Edit or delete the books in the store</li>
    <li>View all processed payments</li>
    <li>Admin can also access the store</li>
    </ul>

<li>Store Zone</li>
    <ul>
    <li>View the top 5 books with the biggest discount</li>
    <li>View all the books in the store</li>
    <li>Search books with a relevant keyword</li>
    <li>Add to shopping cart</li>
    <li>View and edit shopping cart</li>
    <li>Purchase items from shopping cart</li>
    </ul>
</ol>

### Assumptions
<ul>
<li>The admin cannot add/edit a book with a discount of 100% or more.</li>
<li>When a customer adds multiple of the same book - it will be shown as one item in the cart, but the price will be
updated as the amount of copies they added.</li>
<li>The database is named 'ex4.sql'</li>
</ul>
