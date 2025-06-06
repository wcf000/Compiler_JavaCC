import syntaxtree.And;
import syntaxtree.ArrayAssign;
import syntaxtree.ArrayLength;
import syntaxtree.ArrayLookup;
import syntaxtree.Assign;
import syntaxtree.Block;
import syntaxtree.BooleanType;
import syntaxtree.Call;
import syntaxtree.ClassDecl;
import syntaxtree.ClassDeclList;
import syntaxtree.Exp;
import syntaxtree.ExpGroup;
import syntaxtree.ExpList;
import syntaxtree.False;
import syntaxtree.Formal;
import syntaxtree.FormalList;
import syntaxtree.Identifier;
import syntaxtree.IdentifierExp;
import syntaxtree.IdentifierType;
import syntaxtree.If;
import syntaxtree.IntArrayType;
import syntaxtree.IntegerLiteral;
import syntaxtree.IntegerType;
import syntaxtree.LessThan;
import syntaxtree.MainClass;
import syntaxtree.MethodDecl;
import syntaxtree.MethodDeclList;
import syntaxtree.Minus;
import syntaxtree.NewArray;
import syntaxtree.NewObject;
import syntaxtree.Not;
import syntaxtree.Plus;
import syntaxtree.Print;
import syntaxtree.Program;
import syntaxtree.StatementList;
import syntaxtree.This;
import syntaxtree.Times;
import syntaxtree.True;
import syntaxtree.VarDecl;
import syntaxtree.VarDeclList;
import syntaxtree.Visitor;
import syntaxtree.While;

/* 
 * this class uses the Visitor pattern 
 * to convert a miniC program into a Python program
 * 
*/

 public class C2Python_Visitor implements Visitor
 {
 
   private String indentString(int indent) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4*indent; ++i) {
        sb.append(' ');
        }
        return sb.toString();
   }

   public Object visit(Program node, Object data){
     return null;
   }

   public Object visit(MainClass node, Object data){
     return null;
   }
   public Object visit(ClassDecl node, Object data){
     return null;
   }
   public Object visit(VarDecl node, Object data){
	 return null;
   }
   public Object visit(MethodDecl node, Object data){
        // 
        int indent = (int) data; 
        String id = (String) node.i.accept(this,indent);
        String formalList = "";
        if (node.f!=null){
            formalList = (String) node.f.accept(this,indent);
        }
        String varList = "";
        if (node.v!=null){
            varList = (String) node.v.accept(this,indent+1);
        }
        String statementList = "";
        if (node.s!=null){
            statementList = (String) node.s.accept(this,indent+1);
        }
        String expr = (String) node.e.accept(this,indent);
        return indentString(indent) + "def " + id + "(" + formalList + "):\n" + statementList + indentString(indent+1) + "return " + expr;
   } 
   public Object visit(Formal node, Object data){
        // 
        int indent = (int) data; 
        String type = (String) node.t.accept(this,indent);
        String id = (String) node.i.accept(this,indent);
        return id;
   }

   public Object visit(IntArrayType node, Object data){
     return null;
   }
   public Object visit(IntegerType node, Object data){
	 return null;
   }
   public Object visit(BooleanType node, Object data){
	 return null;
   }
   public Object visit(IdentifierType node, Object data){
     return null;
   }
   public Object visit(Block node, Object data){
        // 
        int indent = (int) data; 
        String result="";
        if (node.slist!=null){
            result = (String) node.slist.accept(this,indent+1);
        }
        
        return result;
   }
   public Object visit(If node, Object data) {
	    int indent = (int) data;
	    String expr = (String)node.e.accept(this, indent);
	    String s1 = (String) node.s1.accept(this, indent + 1);
	    String s2 = (String) node.s2.accept(this, indent + 1);
	    return indentString(indent) +
	           "if " + expr + ":\n" +
	           s1 + "\n" +
	           indentString(indent) + "else:\n" +
	           s2;
   }
   public Object visit(While node, Object data){
     return null;
   }
   public Object visit(Print node, Object data) {
	    int indent = (int) data;
	    String e = (String) node.e.accept(this, indent);
	    return indentString(indent) + "print(" + e + ")\n";
	}
   public Object visit(Assign node, Object data){
        // 
        int indent = (int) data; 
        String i = (String) node.i.accept(this,indent);
        String e = (String) node.e.accept(this,indent);
        //--indent;
        return indentString(indent)+i+" = "+e+"\n";
   }

   
   public Object visit(ArrayAssign node, Object data){ 
     Identifier i = node.i;
     Exp e1 = node.e1;
     Exp e2 = node.e2;
     String id = (String) i.accept(this,0);
     String exp1 = (String) e1.accept(this,0);
     String exp2 = (String) e2.accept(this,0);
     data = id+"["+exp1+"] = "+exp2+"\n";
     return data; 
   } 


   public Object visit(And node, Object data){
     return null;
   }

   public Object visit(LessThan node, Object data){
        // 
        int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+"<"+e2;
   }
   public Object visit(Plus node, Object data){
        // 
        int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+" + "+e2;
   }
   public Object visit(Minus node, Object data){
        // 
        int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+" - "+e2;
   }
   public Object visit(Times node, Object data){
        // 
        int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+" * "+e2;
   }
   public Object visit(ArrayLookup node, Object data){
     return null;
   }
   public Object visit(ArrayLength node, Object data){
     return null;
   }
   public Object visit(Call node, Object data){
        // 
        int indent = (int) data; 
        String i = (String) node.i.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);

        return i+"("+e2+")";
   }
   public Object visit(IntegerLiteral node, Object data){
        int indent = (int) data; 
        int i = node.i;
        return " "+i+" ";
   }
   public Object visit(True node, Object data){
        // 
        int indent = (int) data; 
        //--indent;
        return "true";
   }
   public Object visit(False node, Object data){
        // 
        int indent = (int) data; 
        //--indent;
        return "false";
   }
   public Object visit(IdentifierExp node, Object data){

        int indent = (int) data; 
        String s = node.s;
        //--indent;
        return " "+s+" ";
   }
   public Object visit(This node, Object data){
     return null;
   }
   public Object visit(NewArray node, Object data){
     return null;
   }
   public Object visit(NewObject node, Object data){
     return null;
   }

   public Object visit(Not node, Object data){
     return null;
   }
   public Object visit(Identifier node, Object data){

        int indent = (int) data; 
        String s = node.s;
        //--indent;
        return s;
   }

   public Object visit(ExpGroup node, Object data){
     String e="";
     if (node.e!=null){
         e = (String) node.e.accept(this,data);
     }
     return "("+e+")";
   }

   public Object visit(ClassDeclList node, Object data){
     return null;
   }
   public Object visit(ExpList node, Object data){
        // 
        int indent = (int) data; 
        String e = (String) node.e.accept(this,indent);
        if (node.elist!=null){
            String e1 = (String) node.elist.accept(this,indent);
            e = e1 + ","+e;
        }

        return e;
   }
   public Object visit(FormalList node, Object data){
        // 
        int indent = (int) data; 
        String f = (String) node.f.accept(this,indent);
        if (node.flist!=null){
            String f1 = (String) node.flist.accept(this,indent);
            f = f1 +","+f;
        }
        //--indent;
        return f;
   }
   public Object visit(MethodDeclList node, Object data){
        // 
        int indent = (int) data; 
        String m = (String) node.m.accept(this,indent);
        if (node.mlist!=null){
            String m2 = (String) node.mlist.accept(this,indent);
            m = m2 +"\n"+m; 
        }
        return m;
   }
   public Object visit(StatementList node, Object data){
        // 
        int indent = (int) data; 
        String result="";
        
        if (node.slist!=null){
            String s = (String) node.slist.accept(this,indent);
            result = result+s;
        }
        String t = (String) node.s.accept(this,indent);
        //--indent;
        result = result+t;
        return result;
   }
   public Object visit(VarDeclList node, Object data){
        // 
        int indent = (int) data; 
        String v = (String) node.v.accept(this,indent);
        
        if (node.vlist!=null){
            String vlist = (String) node.vlist.accept(this,indent);
            v = vlist + v;
        }
        
        return v;
   }
}

 

 