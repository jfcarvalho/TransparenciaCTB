package com.ctb;

public enum TipoProcesso {
	Pagamento("Pagamento"),
	Renovacao("Renovação"),
	Licitacao("Nova liciação");
	
	private String tipo;

	 TipoProcesso (String tipo)
	 {
	 	this.tipo= tipo;
	 }
	 
	 public String getTipo()
	 {
		 return this.tipo;
	 }
	 
	 public void setTipo(String tipo)
	 {
		 this.tipo = tipo;
	 }

}
