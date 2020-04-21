CREATE FUNCTION TranslateCA
(     @Source      VARCHAR(8000)
   , @ReplaceCharOrder    VARCHAR(8000)
   , @ReplaceWithCharOrder   VARCHAR(8000)
)
   RETURNS VARCHAR(8000) AS  

/*  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *

   Object Name      :   dbo.Translate
   Author         :   UB for DCF, on August05, 2008
   Purpose         :   Like TRANSLATE function in Oracle. Charecter-wise replace in source string with given charecters.
   Input         :   
   Output         :   returns @Translated_Source string 
   Version         :   1.0 as of 08/05/2008
   Modification   :   
   Execute         :   SELECT dbo.Translate('ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890', '1234567890', '0987654321')

 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  */


BEGIN 

   --
   --   Validate input 
   --
   IF   @Source   IS NULL
         RETURN NULL
   
   IF    @Source   = ''
         RETURN ''

   IF    @ReplaceCharOrder IS NULL         OR
      @ReplaceCharOrder = ''
         RETURN @Source

   IF   @ReplaceWithCharOrder IS NULL   
         RETURN 'Invalid parameters in function call dbo.Translate'


   --
   --   Variables used
   --
   DECLARE   @Curr_Pos_In_Source      INT
      , @Check_Source_Str_Len      INT
      , @nth_source         VARCHAR(1)
      , @Found_Match         INT
      , @Translated_Source      VARCHAR(8000)
      , @Match_In_ReplaceWith      VARCHAR(1)


   --
   --   Assign starting values for variables
   --
   SELECT    @Curr_Pos_In_Source      = 1
      , @Check_Source_Str_Len      = LEN(@Source)
      , @Translated_Source      = ''



   --
   --   Replace each charecter with its corrosponding charecter from @ReplaceWithCharOrder
   --
   WHILE @Curr_Pos_In_Source <= @Check_Source_Str_Len
   BEGIN

      --
      --   Get the n'th charecter in @Source
      --
      SELECT @nth_source = SUBSTRING(@Source, @Curr_Pos_In_Source, 1)
   

      --
      --   See if there is a matching character for @nth_source in the @ReplaceCharOrder String, then replace it with 
      --   corrosponding character in @ReplaceWithCharOrder String. If not..go to next n'th character in @Source
      --      If a match is found in @ReplaceCharOrder but no corrosponding character in @ReplaceWithCharOrder
      --      then, replace it with '' (nothing)
      --   Store the resultant string in a separate variable
      --
      SELECT @Found_Match = CHARINDEX(@nth_source, @ReplaceCharOrder COLLATE SQL_Latin1_General_CP1_CS_AS)

      IF @Found_Match > 0
      BEGIN
         --
         --   Finding corrosponding match in the @Found_Match'th position in @ReplaceWithCharOrder
         --   if not found then replace it with '' (nothing)
         --
         SELECT  @Match_In_ReplaceWith = SUBSTRING(@ReplaceWithCharOrder, @Found_Match, 1)

         --
         --   Now replace @nth_source with @Match_In_ReplaceWith and store it in @Translated_Source
         --
         SELECT @Translated_Source = @Translated_Source + @Match_In_ReplaceWith
      END
      ELSE
      BEGIN
         --
         --   No match found in @ReplaceCharOrder
         --
         SELECT @Translated_Source = @Translated_Source + @nth_source
      END

      --
      --   Increment the current position for loop
      --
      SELECT @Curr_Pos_In_Source = @Curr_Pos_In_Source + 1
      
   END
   

   RETURN @Translated_Source

END