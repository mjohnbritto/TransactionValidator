# TransactionValidator

An application to read and validate the Rabobank's transaction statements. The format of the file is a simplified version of the MT940 format. The format is as follows:

<table class="tableblock frame-all grid-all spread">
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Field</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock">Transaction reference</p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">A numeric value</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock">Account number</p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">An IBAN</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock">Start Balance</p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">The starting balance in Euros</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock">Mutation</p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Either an addition (+) or a deduction (-)</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock">Description</p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Free text</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock">End Balance</p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">The end balance in Euros</p></td>
</tr>
</tbody>
</table>


The application also supports two types of files they are CSV and XML

<h2> How to use/consume this application?</h2>
<pre>
Step 1: Clone the project https://github.com/mjohnbritto/TransactionValidator.git
Step 2: Import it to any IDE(Eclipse preferred) as maven project.
Step 3: Build the project(mvn clean install)
Step 4: Since the application is jar packaging you will find the TransactionValidator.jar in the \target folder
You can also download and use the jar file from</pre> https://github.com/mjohnbritto/TransactionValidator/blob/master/src/main/resources/static/Jar/TransactionValidator.jar

 By now you have the executable jar file, Let's see how to use this below.
 
 This application exposes a shell command, <b>validate-transactions</b>. This command has following arguments
    <pre><b>"-N" or "--filename"</b>   --> to specify the name of the statement.
    <b>"-F" or "--fileformat"</b> --> to specify the file format (csv/xml).
    <b>"-I" or "--inputpath"</b>  --> to specify the file path, where the statement file exist.
    <b>"-O" or "--outputpath"</b> --> to specify the file path, where the failed transactios report will be generated.</pre>
  All the arguments are mandatory we need to invoke the <b>validate-transactions</b> command with all arguments. The examples given below.
  
  
  <b>To invoke command for csv type files, the command given below</b>
  
  <pre><em>java -jar TransactionValidator.jar validate-transactions -N records -F csv -I C:\\Users\\johnbrittom\\Downloads\\Assignment\\raboassignment -O C:\\Users\\johnbrittom\\Downloads\\Assignment\\raboassignment\\output</em></pre>
  
  <b>To invoke command for xml type files, the command given below</b>
  
  <pre><em>java -jar TransactionValidator.jar validate-transactions -N records -F xml -I C:\\Users\\johnbrittom\\Downloads\\Assignment\\raboassignment -O C:\\Users\\johnbrittom\\Downloads\\Assignment\\raboassignment\\output</em></pre>
  
  
 <h3>Output - Failed Transactions report</h3>
 The application will validate all the transactions against two conditions as given below.
 <ul>
<li>
<p>all transaction references should be unique</p>
</li>
<li>
<p>the end balance needs to be validated</p>
</li>
</ul>

The failed transactions report will be generated in the given path to the arguments <b>"-O" or "--outputpath"</b>
Sample failed transactions report will look like,

<pre>
<b>Transaction Reference 	 Description </b>
154270                   Candy for Peter de Vries
140269                   Tickets for Vincent Dekker
115137                   Flowers for Jan Theuß 
</pre>
<h3> Resources</h3>
The sample statement files available here https://github.com/mjohnbritto/TransactionValidator/tree/master/src/main/resources/static/input

The sample report files available here https://github.com/mjohnbritto/TransactionValidator/tree/master/src/main/resources/static/output

************************************************** Thank you ********************************************************
