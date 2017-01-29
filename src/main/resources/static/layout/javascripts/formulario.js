$(function() {
	$(".enable-data-processo-input").attr("disabled", "disabled");
	
})

$('.enable-data-processo-botao').on('click', function(event)
{
	 if (!$('.enable-data-processo-botao').is(":checked")) {
	        // Disable elements.
		  $(".enable-data-processo-input").attr("disabled", "disabled");
	      }	
	  if ($('.enable-data-processo-botao').is(":checked")) {
	        // Enable elements.
	        $(".enable-data-processo-input").removeAttr("disabled");
}
});


	

