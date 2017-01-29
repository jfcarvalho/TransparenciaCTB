$(function() {
	$(".enable-aditivo-lancamento-input").attr("disabled", "disabled");
	
})

$('.enable-aditivo-lancamento-botao').on('click', function(event)
{
	 if (!$('.enable-aditivo-lancamento-botao').is(":checked")) {
	        // Disable elements.
		  $(".enable-aditivo-lancamento-input").attr("disabled", "disabled");
	      }	
	  if ($('.enable-aditivo-lancamento-botao').is(":checked")) {
	        // Enable elements.
	        $(".enable-aditivo-lancamento-input").removeAttr("disabled");
}
});


	

