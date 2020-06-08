<template>
  <v-card>
      <v-flex xs12 sm10>
        <v-tree url="/item/category/list"
                :isEdit="isEdit"
                @handleAdd="handleAdd"
                @handleEdit="handleEdit"
                @handleDelete="handleDelete"
                @handleClick="handleClick"
        />
      </v-flex>
  </v-card>
</template>

<script>


  export default {
    name: "category",
    data() {
      return {
        isEdit:true,
              }
    },
    methods: {
      handleAdd(node) {
        console.log("add .... ");
        console.log(node);
        /*this.$http.post("/item/category/addCategory",node)
          .then((res)=>{
            alert(res.data);
          }).catch((error)=>{
            alert("添加失败");
        })*/
      },
      handleEdit(node) {
        //console.log("edit... id: " + id + ", name: " + name)
        console.log("------------------------------");
        console.log(node);
        console.log("------------------------------");

        let id = node.id;
        alert(id);
        if(id==0){
          this.$http.post("/item/category/addCategory",node)
            .then((res)=>{
              if(res.data=="success"){
                alert("添加成功");
              }else if (res.data=="fail"){
                alert("添加失败");
              }
              window.location.reload();
            }).catch((error)=>{
              alert("请求失败");
          })
        }else{
          this.$http.post("/item/category/editCategory",node)
            .then((res)=>{
              if(res.data=="success"){
                alert("修改成功");
              }else if (res.data=="fail"){
                alert("修改失败");
              }
              window.location.reload();
            }).catch((error)=>{
            alert("请求失败");
          })
        }

      },
      handleDelete(id) {
        console.log("delete ... " + id)

        /*this.$http.post("/item/category/deleteById",{
          id:id
        }).then((res)=>{
            }
          ).catch((error)=>{
          alert("删除失败")
        })*/
        this.$http.get("/item/category/deleteById",{params:{id:id}})
          .then((res)=>{
            alert(res.data);
          }).catch((error)=>{
            alert("删除失败");
        })
      },
      handleClick(node) {
        console.log(node)
      }
    }
  };
</script>

<style scoped>

</style>
