package ledax;
import java.util.Iterator;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 27, 2004, Time: 9:00:20 AM
 */
public class MAX_WEIGHT_ASSIGNMENT_T {
  final protected graph G;
  final protected list_node A;
  final protected list_node B;
  final protected edge_array_int c;
  final protected node_array_int pot;
  private node_array_bool free;
  private node_array_edge pred;
  private node_array_int dist;
  private node_pq_int PQ;
  private final static boolean PRUNE = true;
  private int minA;
  private int Delta;
  private stack_node RA;
  private stack_node RB;
  private int upper_bound;
  private boolean upper_bound_is_defined;
  private String error_mssg;
  public MAX_WEIGHT_ASSIGNMENT_T(final graph G
    , final list_node A
    , final list_node B
    , final edge_array_int c) {
    this(G, A, B, c, new node_array_int());
  }
  public MAX_WEIGHT_ASSIGNMENT_T(final graph G
    , final list_node A
    , final list_node B
    , final edge_array_int c
    , final node_array_int pot) {
    this.G = G;
    this.A = A;
    this.B = B;
    this.c = c;
    this.pot = pot;
    pred = new node_array_edge();             //node_array<edge> pred(G,nil);
    dist = new node_array_int(G, 0);          //node_array<NT>   dist(G,0);
    PQ = new node_pq_int();                   //node_pq<NT>      PQ(G);
    free = new node_array_bool(G, true);      //node_array<bool> free(G,true);
    pot.set_forall(B, 0);                     //forall(b,B) pot[b] = 0;
  }
  public list_edge calc() {
    //LOG.trace(this, "apply");
    calc_simple_heuristic();
    list_edge result = new list_edge();       //list<edge> result;
    for (int ia = 0; ia < A.size(); ia++) {   //forall(a,A)
      //LOG.trace(this, "ia=" + ia);
      node a = (node) A.get(ia);
      if (free.getBool(a) &&                 // { if (free[a] &&
        //!max_weight_assignment_augment(G,a,c,pot,free,pred,dist,PQ)) {  // graph has no perfect matching
        !max_weight_assignment_augment(a)) { // graph has no perfect matching
        for (int ib = 0; ib < B.size(); ib++) { //forall(b,B)
          node b = (node) B.get(ib);
          list_edge edges = G.out_edges(b); //forall_out_edges(e,b)  G.rev_edge(e);
          G.forall_rev_edge(edges);
        }
        //list<edge> result;
        return result;                      //return result;  // return empty list
      }
    }
    for (int ib = 0; ib < B.size(); ib++) {   //forall(b,B)
      node b = (node) B.get(ib);
      list_edge edges = G.adj_edges(b);      //forall_adj_edges(e,b) result.append(e);
      for (Iterator it = edges.iterator(); it.hasNext();) {
        edge e = (edge) it.next();
        result.append(e);
      }
    }
    G.forall_rev_edge(result);                //forall(e,result) G.rev_edge(e);
    return result;
  }
  private boolean max_weight_assignment_augment(node a) {
    //LOG.trace(this, "max_weight_assignment_augment");
    initialization(a);
    relax_all_adges_out_of(a);
    if (!augment(a))
      return false;
    potential_update_and_reinitialization();
    return true;
  }
  private void initialization(node a) {
    dist.put(a, 0);                     //dist[a] = 0;
    minA = pot.getInt(a);               //NT   minA           = pot[a];
    Delta = 0;                          //NT   Delta;
    upper_bound = 0;                    //NT   upper_bound = 0;
    upper_bound_is_defined = false;     //= // upper_bound = +\infty
    RA = new stack_node();              //stack<node> RA;
    RA.push(a);                         //=
    RB = new stack_node();              //stack<node> RB;
  }
  private void relax_all_adges_out_of(node a) {
    //LOG.trace(this, "relax_all_adges_out_of(", a.toString());
    //node a1 = a; edge e;
    list_edge edges = G.adj_edges(a);  //forall_adj_edges(e,a1) {
    for (Iterator it = edges.iterator(); it.hasNext();) {
      edge e = (edge) it.next();
      node b = e.target;             //node b = G.target(e);
      //NT db = dist[a1] + (pot[a1] + pot[b] - c[e]);
      int db = dist.getInt(a) + (pot.getInt(a) + pot.getInt(b) - c.getInt(e));
      //LOG.trace(this, "   dist[" + a.toString() + "]=" + dist.getInt(a));
      //LOG.trace(this, "   pot[" + a.toString() + "]=" + pot.getInt(a));
      //LOG.trace(this, "   pot[" + b.toString() + "]=" + pot.getInt(b));
      //LOG.trace(this, "   c[" + e.toString() + "]=" + c.getInt(e));
      //LOG.trace(this, "   db=" + db);
      if (PRUNE && upper_bound_is_defined && db >= upper_bound) //=
        continue;
      if (free.getBool(b)) {           //if (free[b]) {
        upper_bound = db;
        upper_bound_is_defined = true;//=
      }
      if (pred.get(b) == null) {       //if ( pred[b] == nil ) {
        //LOG.trace(this, "   if (pred[b] == null)");
        dist.put(b, db);              //dist[b] = db;
        pred.put(b, e);               //pred[b] = e;
        //LOG.trace(this, "      pred[", b.toString() + "]=" + e.toString());
        RB.push(b);                   //=
        PQ.insert(b, db);             //=
        //LOG.trace(this, "      PQ.insert(", b.toString() + ", " + db);
      }                                //=
      else if (db < dist.getInt(b)) {  //else if ( db < dist[b] ) {
        //LOG.trace(this, "   if (db < dist[b])");
        dist.put(b, db);              //dist[b] = db;
        pred.put(b, e);               //pred[b] = e;
        //LOG.trace(this, "      pred[", b.toString() + "]=" + e.toString());
        PQ.decrease_p(b, db);         //=
        //LOG.trace(this, "      PQ.decrease_p(", b.toString() + ", " + db);
      }
    }
  }
  private boolean augment(node a) {
    //LOG.trace(this, "augment(", a.toString());
    while (true) {                    //=
      //<select from PQ the node b with minimal distance db>
      node b;                          //=
      int db;                          //NT db;
      if (PQ.empty()) {                //=
        return false;                 //=
      }                                //=
      else {                           //=
        b = PQ.del_min();             //=
        db = dist.getInt(b);          //db = dist[b];
        //LOG.trace(this, "   b=PQ.del_min=", b.toString());
        //LOG.trace(this, "   db = dist[b]=", db);
      }                                //=

      //LOG.trace(this, "   free[" + b + "=" + free.getBool(b));
      if (free.getBool(b)) {           //if ( free[b] )
        Delta = db;                   //=
        augment_path_to(b);           //augment_path_to(G,b,pred);
        free.put(a, free.put(b, false)); //free[a] = free[b] = false;
        break;                        //=
      }                                //=
      else {                           //=
        continue_shortest_path_computation(b, db);
      }
    }
    return true;
  }
  private void continue_shortest_path_computation(node b, int db) {
    //LOG.trace(this, "continue_shortest_path_computation(", b.toString() + ", " + db);
    edge e = G.first_adj_edge(b);    //e = G.first_adj_edge(b);
    node a1 = e.target;            //node a1 = G.target(e);
    pred.put(a1, e);                 //pred[a1] = e;
    //LOG.trace(this, "pred.put(", a1.toString() + ", " + e.toString());
    RA.push(a1);                     //=
    //LOG.trace(this, "   RA.push(" + a1 + "=", RA);
    dist.put(a1, db);                //dist[a1] = db;
    int intVal = db + pot.getInt(a1);
    if (minA > intVal) {             //if (db + pot[a1] < minA) {
      minA = intVal;                //minA = db + pot[a1];
    }
    list_edge edges = G.adj_edges(a1); //forall_adj_edges(e,a1)
    for (Iterator it = edges.iterator(); it.hasNext();) {
      e = (edge) it.next();
      b = e.target;               //node b = G.target(e);
      //NT db = dist[a1] + (pot[a1] + pot[b] - c[e]);
      db = dist.getInt(a1) + (pot.getInt(a1) + pot.getInt(b) - c.getInt(e));
      if (PRUNE && upper_bound_is_defined && db >= upper_bound) continue; //=
      if (free.getBool(b)) {        //if (free[b]) {
        upper_bound = db;
        upper_bound_is_defined = true; //=
      }
      if (pred.get(b) == null) {    //if (pred[b] == nil )
        dist.put(b, db);           //dist[b] = db;
        pred.put(b, e);            //pred[b] = e;
        //LOG.trace(this, "pred.put(", b.toString() + ", " + e.toString());
        RB.push(b);                //=
        PQ.insert(b, db);          //=
        //LOG.trace(this, "PQ.insert(" + b.toString() + ", " + db);
      } else if (db < dist.getInt(b)) { //else if ( db < dist[b] ) {
        dist.put(b, db);           //dist[b] = db;
        pred.put(b, e);            //pred[b] = e;
        //LOG.trace(this, "pred.put(", b.toString() + ", " + e.toString());
        PQ.decrease_p(b, db);      //=
        //LOG.trace(this, "PQ.decrease_p(" + b.toString() + ", " + db);
      }
    }
  }
  //      inline void augment_path_to(graph& G, node v, const node_array<edge>& pred)
  private void augment_path_to(node v) {
    //LOG.trace(this, "augment_path_to(", v.toString());
    edge e = (edge) pred.get(v);         //edge e = pred[v];
//     //LOG.trace(this, "e=pred.getLine(" + v.toString() + ")=", e.toString());
//     //LOG.trace(this, "before G=", G.toString());
    while (e != null) {                  //while (e) {
      G.rev_edge(e);                   //=
//        //LOG.trace(this, "G.rev_edge(e)=", e.toString());
      node t = e.target;             //e = pred[G.target(e)]; // not source (!!!)
      //LOG.trace(this, "G.target(e)=", t.toString());
      e = (edge) pred.get(t);
//         if (e != null)
//           //LOG.trace(this, "pred.getLine(t)=", e.toString());
    }
//     //LOG.trace(this, "after G=", G.toString());
  }
  private void potential_update_and_reinitialization() {
    while (!RA.empty()) {                       //=
      //LOG.trace(this, "RA =" + RA);
      node a = (node) RA.pop();                  //node a = RA.pop();
      //LOG.trace(this, "RA.pop() =" + RA);
      pred.put(a, null);                        //pred[a] = nil;
      int pot_change = Delta - dist.getInt(a);  //NT pot_change = Delta - dist[a];
      if (pot_change <= 0)                     //=
        continue;                              //=
      pot_change = pot.getInt(a) - pot_change;
      pot.put(a, pot_change);                   //pot[a] = pot[a] - pot_change;
      //LOG.trace(this, "RA pot[" + a + "]=", pot.getInt(a));
    }                                            //=
    while (!RB.empty()) {                       //=
      node b = (node) RB.pop();                  //node b = RB.pop();
      pred.put(b, null);                        //pred[b] = nil;
      if (PQ.member(b))                         //=
        PQ.del(b);                             //=
      int pot_change = Delta - dist.getInt(b);  //NT pot_change = Delta - dist[b];
      if (pot_change <= 0)                     //=
        continue;                              //=
      pot.put(b, pot.getInt(b) + pot_change);   //pot[b] = pot[b] + pot_change;
      //LOG.trace(this, "RB pot[" + b + "]=", pot.getInt(b));
    }
  }
  public void calc_simple_heuristic() {
    for (int ia = 0; ia < A.size(); ia++) {//forall(a,A) {
      node a = (node) A.get(ia);
      edge e_max = null;                  //edge e_max = nil;
      int C_max = 0;                      //NT C_max = 0;
      list_edge edges = G.adj_edges(a);   //forall_adj_edges(e,a){
      for (Iterator it = edges.iterator(); it.hasNext();) {
        edge e = (edge) it.next();
        int curr = c.getInt(e);
        if (C_max < curr) {              //if (c[e] >  C_max) {
          e_max = e;                    //=
          C_max = curr;                 //C_max = c[e];
        }                                //=
      }                                   //=
      pot.put(a, C_max);                  //pot[a] = C_max;
      //LOG.trace(this, "pot[" + a + "]=", pot.getInt(a));
      node b = null;
      boolean rev = false;
      if (e_max != null) {
        b = e_max.target;              //b = G.target(e_max);
        rev = free.getBool(b);
      }
      if (rev) { //if ( e_max != nil && free[b = G.target(e_max)] ) {
        G.rev_edge(e_max);               //=
        free.put(a, free.put(b, false)); //free[a] = free[target] = false;
      }
    }
  }
  //bool CHECK_ASSIGNMENT_T(const graph& G, const edge_array<NT>& c,
  //        const list<edge>& M, const node_array<NT>& pot, int max_cost)
  protected boolean check(list_edge M, boolean max_cost) {
    //node v; edge e;                                  //=
    error_mssg = "CHECK_ASSIGNMENT_T";                 //=
    if (max_cost) error_mssg += "(max): ";
    else error_mssg += "(min): "; //=
    boolean res = true;                                //=
    node_array_int deg_in_M = new node_array_int(G, 0);//node_array<int> deg_in_M(G,0);
    for (Iterator it = M.iterator(); it.hasNext();) { //forall(e, M) {
      edge e = (edge) it.next();
      node v = e.source;                            //node v = G.source(e);
      deg_in_M.put(v, deg_in_M.getInt(v) + 1);        //deg_in_M[G.source(e)]++;
      v = e.target;                                 //v = G.target(e);
      deg_in_M.put(v, deg_in_M.getInt(v) + 1);        //deg_in_M[G.target(e)]++;
    }                                                  //=
    list_node nodes = G.list_node();                   //forall_nodes(v,G)
    for (Iterator it = nodes.iterator(); it.hasNext();) {
      node v = (node) it.next();
      //res = res && leda_assert(deg_in_M[v] <= 1,error_mssg + "M is not a matching");
      if (deg_in_M.getInt(v) > 1) {
        error_mssg += "M is not a matching";
        res = false;
        break;
      }
      // dk: MOVED inside the "nodes" loop
      list_edge edges = G.adj_edges(v);               //forall_edges(e,G)
      for (Iterator itE = edges.iterator(); itE.hasNext();) {
        edge e = (edge) itE.next();
        //node v = G.source(e);
        node w = e.target;                         //node w = G.target(e);
        if (max_cost) {
          //res = res && leda_assert(c[e] <= pot[v] + pot[w], error_mssg + "illegal reduced cost"); }
          if (c.getInt(e) > pot.getInt(v) + pot.getInt(w)) {
            error_mssg += "illegal reduced cost";
            res = false;
            break;
          }
        } else {
          //res = res && leda_assert(c[e] >= pot[v] + pot[w], error_mssg + "illegal reduced cost"); }
          if (c.getInt(e) < pot.getInt(v) + pot.getInt(w)) {
            error_mssg += "illegal reduced cost";
            res = false;
            break;
          }
        }
      }
    }
    //LOG.trace(this, "c=", c.toString());
    for (Iterator itM = M.iterator(); itM.hasNext();) {//forall(e,M)
      edge e = (edge) itM.next();
      node v = e.source;                            //node v = G.source(e);
      node w = e.target;                            //node w = G.target(e);
      //res = res && leda_assert(c[e] == pot[v] + pot[w], error_mssg + "non_tight edge in M");
      int potV = pot.getInt(v);
      int potW = pot.getInt(w);
      int cE = c.getInt(e);
      if (cE != potV + potW) {
        error_mssg += "non_tight edge in M";
        res = false;
        break;
      }
    }
    return res;
  }
  public node_array_bool getFree() {
    return free;
  }
  public String getError() {
    return error_mssg;
  }
  public int getTotalCost(list_edge M) {
    int cost = 0;
    for (Iterator itM = M.iterator(); itM.hasNext();) {
      edge e = (edge) itM.next();
      cost += c.getInt(e);
    }
    return cost;
  }
}

/*
template <class NT>
__temp_func_inline
bool CHECK_ASSIGNMENT_T(const graph& G, const edge_array<NT>& c,
const list<edge>& M, const node_array<NT>& pot, int max_cost)
{ node v; edge e;
string error_mssg = "CHECK_ASSIGNMENT_T";
if ( max_cost ) error_mssg += "(max): "; else error_mssg += "(min): ";
bool res = true;

node_array<int> deg_in_M(G,0);
forall(e,M)
{ deg_in_M[G.source(e)]++;
deg_in_M[G.target(e)]++;
}
forall_nodes(v,G) res = res && leda_assert(deg_in_M[v] <= 1,error_mssg + "M is not a matching");
forall_edges(e,G)
{ node v = G.source(e); node w = G.target(e);
if (max_cost)
{ res = res && leda_assert(c[e] <= pot[v] + pot[w],error_mssg + "illegal reduced cost"); }
else
{ res = res && leda_assert(c[e] >= pot[v] + pot[w],error_mssg + "illegal reduced cost"); }

}
forall(e,M)
{ node v = G.source(e); node w = G.target(e);
res = res && leda_assert(c[e] == pot[v] + pot[w],error_mssg + "non_tight edge in M");
}
return res;
}
*/

/* template <class NT> __temp_func_inline
list<edge> MAX_WEIGHT_ASSIGNMENT_T(graph& G,
const list<node>& A,
const list<node>& B,
const edge_array<NT>& c,
node_array<NT>& pot)
{ node source,target; edge e;
// check that all edges are directed from A to B
forall(target,B) assert(G.outdeg(target) == 0);
node_array<bool> free(G,true);
node_array<edge> pred(G,nil);
node_array<NT>   dist(G,0);
node_pq<NT>      PQ(G);
forall(target,B) pot[target] = 0;
switch (which_heuristic)
{ case 0: { // no heuristic
NT C = 0;
forall_edges(e,G) if (c[e] > C) C = c[e];
forall(source,A)       pot[source] = C;
break;
}
case 1: { // simple heuristic
forall(source,A)
{ edge e_max = nil; NT C_max = 0;
forall_adj_edges(e,source)
if (c[e] >  C_max) { e_max = e; C_max = c[e]; }
pot[source] = C_max;
if ( e_max != nil && free[target = G.target(e_max)] )
{ G.rev_edge(e_max);
free[source] = free[target] = false;
}
}
break;
}
default: { // refined heuristic
mwbm_heuristic( G, A, c, pot, free);
break;
}
}
list<edge> result;
forall(source,A)
{ if (free[source] &&
!max_weight_assignment_augment(G,source,c,pot,free,pred,dist,PQ))
{ // graph has no perfect matching
forall(target,B)
forall_out_edges(e,target) G.rev_edge(e);
list<edge> result;
return result;  // return empty list
}
}
forall(target,B)
forall_adj_edges(e,target) result.append(e);
forall(e,result) G.rev_edge(e);
return result;
}  */

/*
template <class NT>
__temp_func_inline
bool max_weight_assignment_augment(graph& G,
node a, const edge_array<NT>& c,
node_array<NT>& pot, node_array<bool>& free,
node_array<edge>& pred, node_array<NT>& dist,
node_pq<NT>& PQ)
{
dist[a] = 0;

//node best_node_in_A = a;
NT   minA           = pot[a];
NT   Delta;

// mod. 12/2000 by GS
NT   upper_bound = 0;
bool upper_bound_is_defined = false;  // upper_bound = +\infty

stack<node> RA;  RA.push(a);
stack<node> RB;

node a1 = a; edge e;

forall_adj_edges(e,a1)
{ node b = G.target(e);
NT db = dist[a1] + (pot[a1] + pot[b] - c[e]);

// mod. 12/2000 by GS
if (PRUNE && upper_bound_is_defined && db >= upper_bound) continue;
if (free[b]) { upper_bound = db; upper_bound_is_defined = true; }

if ( pred[b] == nil )
{ dist[b] = db; pred[b] = e; RB.push(b);
PQ.insert(b,db);
}
else
if ( db < dist[b] )
{ dist[b] = db; pred[b] = e;
PQ.decrease_p(b,db);
}
}



while ( true )
{ node b; NT db;
if (PQ.empty()) { return false; }
else { b = PQ.del_min(); db = dist[b]; }

if ( free[b] )
{ Delta = db;

augment_path_to(G,b,pred);
free[a] = free[b] = false;
break;

}
else
{
e = G.first_adj_edge(b);
node a1 = G.target(e);
pred[a1] = e; RA.push(a1);
dist[a1] = db;

if (db + pot[a1] < minA)
{ //best_node_in_A = a1;
minA = db + pot[a1];
}

forall_adj_edges(e,a1)
{ node b = G.target(e);
NT db = dist[a1] + (pot[a1] + pot[b] - c[e]);

// mod. 12/2000 by GS
if (PRUNE && upper_bound_is_defined && db >= upper_bound) continue;
if (free[b]) { upper_bound = db; upper_bound_is_defined = true; }

if ( pred[b] == nil )
{ dist[b] = db; pred[b] = e; RB.push(b);
PQ.insert(b,db);
}
else
if ( db < dist[b] )
{ dist[b] = db; pred[b] = e;
PQ.decrease_p(b,db);
}
}
}
}

while (!RA.empty() )
{ node a = RA.pop();
pred[a] = nil;
NT pot_change = Delta - dist[a];
if (pot_change <= 0 ) continue;
pot[a] = pot[a] - pot_change;
}

while (!RB.empty() )
{ node b = RB.pop();
pred[b] = nil;
if (PQ.member(b)) PQ.del(b);
NT pot_change = Delta - dist[b];
if (pot_change <= 0 ) continue;
pot[b] = pot[b] + pot_change;
}
return true;
}
*/
