# TransactionValidator

An application to read and validate the Rabobank's transaction statements. The format of the file is a simplified version of the MT940 format. The format is as follows:

<table class="tableblock frame-all grid-all spread">
<caption class="title">Table 1. Record description</caption>
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
