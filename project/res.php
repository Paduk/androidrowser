<?php
require_once $_SERVER['DOCUMENT_ROOT'].'/preset.php';


$q = "SELECT * FROM Maintable";
$result = $mysqli->query( $q);
$total_record = $result->num_rows;



?>
        목록<br />
	
<?php if($total_record==0) :?>
    글이 없습니다.
<?php else :?>
<?php
{
 if( isset($page) ) {
    $now_page = $page;
	}
else {
    $now_page = 1;
}   
$record_per_page = 20;
$start_record = $record_per_page*($now_page-1);
$record_to_get = $record_per_page;

if( $start_record+$record_to_get > $total_record) {
  $record_to_get = $total_record - $start_record;
}

$q = "SELECT * FROM Maintable WHERE 1 ORDER BY idx DESC LIMIT $start_record, $record_to_get";
$result = $mysqli->query($q);

} 
?>          
<?php endif?>

		<table class="table">
			<thead>
				<th>idx</th>
				<th>type</th>
				<th>number</th>
				<th>text</th>
			</thead>
		<?php while($data = $result->fetch_array()) :?>
			<tr>
				<td><?php echo $data['idx']?></td>
				<td><?php echo $data['type']?></td>
				<td><?php echo $data['number']?></td>
				<td><?php echo $data['text']?></td>
			</tr>
			
		<?php endwhile ?>
		</table>
        <div class="pagination">
					<ul>
				<?php
				$page_per_block = 5;
				$now_block = ceil($now_page / $page_per_block);
				
				$total_page = ceil($total_record / $record_per_page);
				$total_block = ceil($total_page / $page_per_block);

				if(1<$now_block ) {
				  $pre_page = ($now_block-1)*$page_per_block;
				  echo '<a href="http://'.$_SERVER['HTTP_HOST'].'/project/res.php?page='.$pre_page.'">이전</a>';

				}

				$start_page = ($now_block-1)*$page_per_block+1;
				$end_page = $now_block*$page_per_block;
				if($end_page>$total_page) {
				  $end_page = $total_page;
				}

				?>
					
				<?php for($i=$start_page;$i<=$end_page;$i++) :?>
					<li><a href="/project/res.php?id=<?php echo $id ?>&page=<?php echo $i; ?>"><?php echo $i; ?></a></li>
				<?php endfor?>
				</ul>
				<?php
				if($now_block < $total_block) {
				  $post_page = ($now_block)*$page_per_block+1;
				  echo '<a href="http://'.$_SERVER['HTTP_HOST'].'/project/res.php?page='.$post_page.'">다음</a>';
				}

				?>
				</div><!-- .pagination -->
